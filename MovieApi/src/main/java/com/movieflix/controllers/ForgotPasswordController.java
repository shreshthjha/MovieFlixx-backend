package com.movieflix.controllers;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieflix.auth.entities.ForgotPassword;
import com.movieflix.auth.entities.User;
import com.movieflix.auth.repositories.ForgotPasswordRepository;
import com.movieflix.auth.repositories.UserRepository;
import com.movieflix.auth.utils.ChangePassword;
import com.movieflix.dto.MailBody;
import com.movieflix.services.EmailService;

@RestController
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {
	
	private final UserRepository userRepository;
	
	private final EmailService emailService;
	
	private final ForgotPasswordRepository forgotPasswordRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	
	
	public ForgotPasswordController(UserRepository userRepository, EmailService emailService,
			ForgotPasswordRepository forgotPasswordRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.emailService = emailService;
		this.forgotPasswordRepository = forgotPasswordRepository;
		this.passwordEncoder = passwordEncoder;
	}



	// send mail for email verification
	@PostMapping("/verifyMail/{email}")
	public ResponseEntity<String> verifyEmail(@PathVariable String email) {
	    User user = userRepository.findByEmail(email).orElseThrow(()
	            -> new UsernameNotFoundException("Please provide a valid email"));

	    int otp = otpGenerator();
	    MailBody mailBody = MailBody.builder()
	            .to(email)
	            .text("This is the OTP for your Forgot Password request: " + otp)
	            .subject("OTP for Forgot Password request")
	            .build();

	    // Check if a ForgotPassword record already exists for the user
	    ForgotPassword fp = forgotPasswordRepository.findByUser(user)
	            .orElse(ForgotPassword.builder().user(user).build());

	    fp.setOtp(otp);
	    fp.setExpirationTime(new Date(System.currentTimeMillis() + 20 * 100000));

	    emailService.sendSimpleMessage(mailBody);
	    forgotPasswordRepository.save(fp); // Save or update record

	    return ResponseEntity.ok("Email sent for Verification!");
	}

	
	//verifying otp 
	@PostMapping("verifyOtp/{otp}/{email}")
	public ResponseEntity<String> verifyOtp (@PathVariable Integer otp, @PathVariable String email){
		
		User user = userRepository.findByEmail(email).orElseThrow(()
				-> new UsernameNotFoundException("Please provide valid email"));
		
		ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user).orElseThrow(() ->
									new RuntimeException("Invalid Otp for email :" + email));
		
		if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
			forgotPasswordRepository.deleteById(fp.getFpid());
			return new ResponseEntity<>("OTP has expired !", HttpStatus.EXPECTATION_FAILED);
		}
		
		return ResponseEntity.ok("OTP verified!");
		
	}
	
	
	@PostMapping("/changePassword/{email}")
	public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword, @PathVariable String email){
		
		if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
			return new ResponseEntity<>("Password and RepeatPassword do not matched !", HttpStatus.EXPECTATION_FAILED);
		}
		
		String encodedPassword = passwordEncoder.encode(changePassword.password());
		
		userRepository.updatePassword(email, encodedPassword);
		
		return ResponseEntity.ok("Password has been changed!");
	}
	
	private Integer otpGenerator() {
		Random random = new Random();
		return random.nextInt(100_000, 999_999);
	}
}

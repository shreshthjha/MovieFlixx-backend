package com.movieflix.auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.movieflix.auth.entities.ForgotPassword;
import com.movieflix.auth.entities.User;



public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {
	
	@Query("select fp from ForgotPassword fp  where fp.otp = ?1 and fp.user = ?2")
	Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);
	
	// Add this method to find the ForgotPassword record by User
    Optional<ForgotPassword> findByUser(User user);
}

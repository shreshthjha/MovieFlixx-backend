package com.movieflix.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.movieflix.dto.MailBody;

@Service
public class EmailService {
	
	private final JavaMailSender javaMailSender;

	public EmailService(JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	}
	
	public void sendSimpleMessage(MailBody mailBody) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mailBody.to());
		message.setFrom("shreshthjha111@gmail.com");
		message.setSubject(mailBody.subject());
		message.setText(mailBody.text());
		
		javaMailSender.send(message);
	}
	
}

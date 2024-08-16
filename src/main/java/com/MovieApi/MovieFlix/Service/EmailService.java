package com.MovieApi.MovieFlix.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.MovieApi.MovieFlix.Security.Entity.MailBody;

@Service
public class EmailService {

	private JavaMailSender mailSender;

	public EmailService(JavaMailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}

	public void SendMail(MailBody user) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.to());
		message.setSubject(user.subject());
		message.setFrom("acogination@gmail.com");
		message.setText(user.text());

		mailSender.send(message);
	}
}

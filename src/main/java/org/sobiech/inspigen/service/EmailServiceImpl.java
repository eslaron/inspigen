package org.sobiech.inspigen.service;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	JavaMailSenderImpl mailSender;
	
	@Override
	public void  sendTokenMail(String email, String tokenType, String token) {
		
		MimeMessage message =  mailSender.createMimeMessage();
		MimeMessageHelper mimeHelper;
		try {
			mimeHelper = new MimeMessageHelper(message,true);
			mimeHelper.setTo(email);
			
			
			mimeHelper.setFrom("sebastian.sobiech@gmail.com");
			
			if (tokenType == "activationToken")
			mimeHelper.setSubject("Witamy w systemie Inspigen!");
			mimeHelper.setText(
					"<html><body>Hej :)<br/>Cieszymy si�, �e do nas do��czy�e�."
					+ "<br/>Kliknij w podany link, aby aktywowa� swoje konto: "
					+ "<a href='http://localhost:8080/inspigen/activateAccount/?token="
					+token+"'>LINK</a></body></html>",true);
			mailSender.send(message);
		} catch (MessagingException e) {
			System.out.println("Error Sending email "+ e.getMessage());
		}
		
	}
}
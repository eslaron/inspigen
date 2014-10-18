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
					"<html><body>Hej :)<br/>Cieszymy się, że do nas dołączyłeś."
					+ "<br/>Kliknij w podany link, aby aktywować swoje konto: "
					+ "<a href='http://vps106107.ovh.net:8080/inspigen/activateAccount/"
					+token+"'>LINK</a></body></html>",true);
			mailSender.send(message);
		} catch (MessagingException e) {
			System.out.println("Error Sending email "+ e.getMessage());
		}
		
	}
}
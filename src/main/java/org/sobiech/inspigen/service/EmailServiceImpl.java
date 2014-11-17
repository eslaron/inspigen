package org.sobiech.inspigen.service;


import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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
		
		String msg = "";
		
		Multipart mp = new MimeMultipart();
		MimeBodyPart mbp = new MimeBodyPart();
		
		
		MimeMessage message =  mailSender.createMimeMessage();
		MimeMessageHelper mimeHelper;
		try {
			mimeHelper = new MimeMessageHelper(message,true);
			mimeHelper.setTo(email);
			
			mimeHelper.setFrom("sebastian.sobiech@gmail.com");
			
			if (tokenType == "activationToken") {
			mimeHelper.setSubject("Witamy w systemie Inspigen!");
			
			msg = "<html><body>Hej :)<br/>Cieszymy się, że do nas dołączyłeś."
					+ "<br/>Kliknij w podany link, aby aktywować swoje konto: "
					+ "<a href='http://localhost:8080/inspigen/#/activateAccount/"
					+token+"'>LINK</a></body></html>";
			
			mbp.setContent(msg, "text/html; charset=UTF-8");
			mp.addBodyPart(mbp);
			message.setContent(mp);
			
			/*mimeHelper.setText(
					"<html><body>Hej :)<br/>Cieszymy się, że do nas dołączyłeś."
					+ "<br/>Kliknij w podany link, aby aktywować swoje konto: "
					+ "<a href='http://localhost:8080/inspigen/#/activateAccount/"
					+token+"'>LINK</a></body></html>",true);*/
			}
			
			if (tokenType == "passwordToken") {
				mimeHelper.setSubject("Przypomnienie hasła - Inspigen");
				mimeHelper.setText(
						"<html><body>Hej :)<br/>Aby zresetować swoje hasło kliknij w poniższy link: <br/>"
						+ "<a href='http://localhost:8080/inspigen/#/resetPassword/"
						+token+"'>LINK</a></body></html>",true);
			}
			
			mailSender.send(message);
		} catch (MessagingException e) {
			System.out.println("Error Sending email "+ e.getMessage());
		}
		
	}
}
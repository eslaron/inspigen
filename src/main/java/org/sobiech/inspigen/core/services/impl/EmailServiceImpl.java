package org.sobiech.inspigen.core.services.impl;


import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.sobiech.inspigen.core.models.entities.Settings;
import org.sobiech.inspigen.core.services.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

//Klasa implementujaca interfejs IAttachmentService
@Service
public class EmailServiceImpl implements IEmailService {
	
	@Autowired
	JavaMailSenderImpl mailSender;
	
	@Autowired
	Settings settings;
	
	//Implementacja metody wysyłąjące emaila z linkiem zawierającym odpowiedni token
	@Override
	public void  sendTokenMail(String email, String tokenType, String token) {
		
		String msg = "";
		
		Multipart mp = new MimeMultipart();
		MimeBodyPart mbp = new MimeBodyPart();
		
		//Tworzmy nową wiadomość
		MimeMessage message =  mailSender.createMimeMessage();
		MimeMessageHelper mimeHelper;
		try {
			mimeHelper = new MimeMessageHelper(message,true);
			
			//Ustawiamy odbiorcę
			mimeHelper.setTo(email);
			
			//Ustawiamy nadawcę
			mimeHelper.setFrom(settings.getEmailAddress());
			
			//Jeśli typ tokena to token aktywacyjny, to treść wiadmości jest następująca
			if (tokenType == "activationToken") {
				mimeHelper.setSubject("Witamy w systemie Inspigen!");
			
				msg = "<html><body>Hej :)<br/>Cieszymy się, że do nas dołączyłeś."
						+ "<br/>Kliknij w podany link, aby aktywować swoje konto: "
						+ "<a href='http://localhost:8080/inspigen/#/activateAccount/"
						+token+"'>LINK</a></body></html>";
			
				//Ustawiamy kodowanie wiadomości
				mbp.setContent(msg, "text/html; charset=UTF-8");
				mp.addBodyPart(mbp);
				message.setContent(mp);
			}
			
			//Jeśli typ tokena to token hasła, to treść wiadmości jest następująca
			if (tokenType == "passwordToken") {
				mimeHelper.setSubject("Reset hasła - Inspigen");
				
				msg = "<html><body>Hej :)<br/>Aby zresetować swoje hasło kliknij w poniższy link: <br/>"
						+ "<a href='http://localhost:8080/inspigen/#/newPassword/"
						+token+"'>LINK</a></body></html>";
				
				//Ustawiamy kodowanie wiadomości
				mbp.setContent(msg, "text/html; charset=UTF-8");
				mp.addBodyPart(mbp);
				message.setContent(mp);
			}
			
			//Wysyłamy wiadomość
			mailSender.send(message);
		} catch (MessagingException e) {
			System.out.println("Error Sending email "+ e.getMessage());
		}		
	}
}
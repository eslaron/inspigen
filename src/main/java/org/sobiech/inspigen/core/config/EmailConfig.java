package org.sobiech.inspigen.core.config;

import java.util.Properties;

import org.sobiech.inspigen.core.models.entity.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

//Klasa konfigurująca wysyłanie wiadomości email
@Configuration
public class EmailConfig {

	//Bean z konfiguracją mailsendera
	@Bean
	JavaMailSenderImpl mailSender() {
		
		//Podajemy dane serwera pocztowego
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("smtp.gmail.com");
		sender.setPort(587);
		sender.setUsername("system.inspigen");
		sender.setPassword("qwertyui7");
		
		//Ustawiamy dodatkowe opcje
		Properties javaMailProperties = new Properties();	
		javaMailProperties.setProperty("mail.smtp.auth", "true");
		javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
		
		//Odkomentować w razie, gdyby serwer poczty na serwerze VPS odrzucał połączenie
		//javaMailProperties.setProperty("mail.smtp.ssl.trust", settings.getEmailHost());
	
		sender.setJavaMailProperties(javaMailProperties);
		
		return sender;
	}	
}
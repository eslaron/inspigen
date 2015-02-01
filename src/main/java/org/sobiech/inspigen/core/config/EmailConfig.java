package org.sobiech.inspigen.core.config;

import java.util.Properties;

import org.sobiech.inspigen.core.models.entities.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

//Klasa konfigurująca wysyłanie wiadomości email
@Configuration
public class EmailConfig {
	
	@Autowired
	Settings settings;
	
	//Bean z konfiguracją mailsendera
	@Bean
	JavaMailSenderImpl mailSender() {
		
		//Podajemy dane serwera pocztowego
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(settings.getEmailHost());
		sender.setPort(settings.getEmailPort());
		sender.setUsername(settings.getEmailUsername());
		sender.setPassword(settings.getEmailPassword());
		
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
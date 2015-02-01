package org.sobiech.inspigen.core.config;

import java.util.Properties;

import org.sobiech.inspigen.core.models.entities.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {
	
	@Autowired
	Settings settings;
	
	@Bean
	JavaMailSenderImpl mailSender() {
		
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(settings.getEmailHost());
		sender.setPort(settings.getEmailPort());
		sender.setUsername(settings.getEmailUsername());
		sender.setPassword(settings.getEmailPassword());
		
		Properties javaMailProperties = new Properties();
		
		javaMailProperties.setProperty("mail.smtp.auth", "true");
		javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
		//javaMailProperties.setProperty("mail.smtp.ssl.trust", settings.getEmailHost());
	
		sender.setJavaMailProperties(javaMailProperties);
		
		return sender;
	}	
}
package com.devrebel.inspigen.core.config.email;

import java.util.Properties;

import com.devrebel.inspigen.app.domain.settings.Settings;
import com.devrebel.inspigen.app.domain.settings.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

	@Autowired
	SettingsRepository repository;

	@Bean
	JavaMailSenderImpl mailSender() {

		Settings settings = repository.findOne(1L);
		//TODO: Create a encryption/decryption module for given string. In this case for email config passsword.

		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(settings.getEmailHost());
		sender.setPort(settings.getEmailPort());
		sender.setUsername(settings.getEmailUsername());
		sender.setPassword(settings.getEmailPassword());

		Properties javaMailProperties = new Properties();	
		javaMailProperties.setProperty("mail.smtp.auth", "true");
		javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");

		sender.setJavaMailProperties(javaMailProperties);
		
		return sender;
	}	
}
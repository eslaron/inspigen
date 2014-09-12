package org.sobiech.inspigen.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {
	
	private String host = "smtp.gmail.com";
	private int port = 587;
	private String username = "sebastian.sobiech";
	private String password = "maciorado13lat";
	
	
	@Bean
	JavaMailSenderImpl mailSender() {
		
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(host);
		sender.setPort(port);
		sender.setUsername(username);
		sender.setPassword(password);
		
		Properties javaMailProperties = new Properties();
		
		javaMailProperties.setProperty("mail.smtp.auth", "true");
		javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
		
		sender.setJavaMailProperties(javaMailProperties);
		
		return sender;
	}	
}
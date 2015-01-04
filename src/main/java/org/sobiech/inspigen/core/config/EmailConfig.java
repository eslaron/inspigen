package org.sobiech.inspigen.core.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {
	
	private String host = "mail.inspigen.pl";
	private int port = 25;
	private String username = "administracja";
	private String password = "Sebastian999";
	
	
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
		//javaMailProperties.setProperty("mail.smtp.ssl.trust", "mail.inspigen.pl");
	
		sender.setJavaMailProperties(javaMailProperties);
		
		return sender;
	}	
}
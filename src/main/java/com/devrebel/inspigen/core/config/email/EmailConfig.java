package com.devrebel.inspigen.core.config.email;

import com.devrebel.inspigen.app.domain.settings.Settings;
import com.devrebel.inspigen.app.domain.settings.SettingsRepository;
import com.devrebel.inspigen.core.config.encryption.TextEncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Lazy
@Configuration
public class EmailConfig {

	private static final long SYSTEM_SETTINGS_ID = 1L;

	@Autowired
	SettingsRepository repository;

    @Autowired
    TextEncryptionService textEncryptionService;

	@Bean
	JavaMailSenderImpl mailSender() throws Exception {
		Settings settings = repository.findOne(SYSTEM_SETTINGS_ID);
        String encryptedEmailPassword = settings.getEmailPassword();
        String decryptedEmailPassword = textEncryptionService.decrypt(encryptedEmailPassword);

		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(settings.getEmailHost());
		sender.setPort(settings.getEmailPort());
		sender.setUsername(settings.getEmailUsername());
		sender.setPassword(decryptedEmailPassword);

		Properties javaMailProperties = new Properties();

        //If authentication is required
        javaMailProperties.setProperty("mail.smtp.auth", "true");

        //TLS support
        javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");

        //SSL support
        final String sslPort = String.valueOf(settings.getEmailPort());
        javaMailProperties.setProperty("mail.smtp.socketFactory.port", sslPort);
        javaMailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.setProperty("mail.smtp.socketFactory.fallback", "false");

		sender.setJavaMailProperties(javaMailProperties);
		
		return sender;
	}	
}
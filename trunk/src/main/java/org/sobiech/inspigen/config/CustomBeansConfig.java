package org.sobiech.inspigen.config;

import org.sobiech.inspigen.model.LoginAttempts;
import org.sobiech.inspigen.model.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBeansConfig {
		
    @Bean
    Settings getSettings() {
    	Settings settings = new Settings();
    	return settings;
    }
    
    @Bean
    LoginAttempts getLoginAttempts() {
    	LoginAttempts attempts = new LoginAttempts();
    	return attempts;
    }
}
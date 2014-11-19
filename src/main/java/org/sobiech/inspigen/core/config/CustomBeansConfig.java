package org.sobiech.inspigen.core.config;

import org.sobiech.inspigen.core.models.entities.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBeansConfig {
		
    @Bean
    Settings getSettings() {
    	Settings settings = new Settings();
    	return settings;
    }
}
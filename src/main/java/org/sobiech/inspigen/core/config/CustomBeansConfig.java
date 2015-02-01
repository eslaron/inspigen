package org.sobiech.inspigen.core.config;

import org.sobiech.inspigen.core.models.entities.Settings;
import org.sobiech.inspigen.core.services.ISettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBeansConfig {
		
	@Autowired
	ISettingsService settingsService;
	
    @Bean
    public Settings getSettings() {
    	Settings settings = settingsService.getSettings().get(0);
    	return settings;
    }
}
package org.sobiech.inspigen.core.config;

import javax.annotation.PostConstruct;

import org.sobiech.inspigen.core.models.entities.Settings;
import org.sobiech.inspigen.core.services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;

public class AppSettings {
	
	@Autowired
	Settings settings;
	
	@Autowired
	SettingsService settingsService;
	
	@PostConstruct
	public void initSettings() {
		settings = settingsService.getSettings().get(0);
	}	
}
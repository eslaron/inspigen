package org.sobiech.inspigen.core.config;

import javax.annotation.PostConstruct;

import org.sobiech.inspigen.core.models.entities.Settings;
import org.sobiech.inspigen.core.services.ISettingsService;
import org.springframework.beans.factory.annotation.Autowired;

public class AppSettings {
	
	@Autowired
	Settings settings;
	
	@Autowired
	ISettingsService settingsService;
	
	@PostConstruct
	public void initSettings() {
		//DELETE THIS CLASS
	}	
}
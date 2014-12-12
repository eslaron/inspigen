package org.sobiech.inspigen.core.services;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Settings;

public interface SettingsService {
	
	public List<Settings> getSettings();
	
	public void updateSettings(Settings data);
}
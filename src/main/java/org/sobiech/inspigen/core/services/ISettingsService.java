package org.sobiech.inspigen.core.services;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Settings;

//Interfejs zawierajacy prototypy metod operujących na klasie Settings
public interface ISettingsService {
	
	public List<Settings> getSettings();			//Pobieranie ustawień z bazy danych
	
	public void updateSettings(Settings data); 		//Aktualizacja ustawień
}
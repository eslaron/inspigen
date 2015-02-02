package org.sobiech.inspigen.core.services.impl;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Settings;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.services.ISettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Klasa implementujaca interfejs ISettingsService
@Service
@Transactional
public class SettingsServiceImpl implements ISettingsService {

	IGenericDao<Settings> dao;
	 
	//Ustawienie klasy, na ktorej ma operowac DAO
	   @Autowired
	   public void setDao(IGenericDao<Settings> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(Settings.class);
	   }
	
	//Implementacja pobierania ustawień z bazy   
	@Override
	public List<Settings> getSettings() {		
		return dao.findAll();
	}

	//Implementacja aktualizowania ustawień
	@Override
	public void updateSettings(Settings data) {
		dao.update(data);
	}	
}
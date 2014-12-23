package org.sobiech.inspigen.core.services.impl;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Settings;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.services.ISettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SettingsServiceImpl implements ISettingsService {

	IGenericDao<Settings> dao;
	 
	   @Autowired
	   public void setDao(IGenericDao<Settings> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(Settings.class);
	   }
	
	@Override
	public List<Settings> getSettings() {		
		return dao.findAll();
	}

	@Override
	public void updateSettings(Settings data) {
		dao.update(data);
	}	
}
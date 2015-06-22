package org.sobiech.inspigen.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.core.models.entity.Location;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.services.ILocationService;

//Klasa implementujaca interfejs ILocationService
@Service
@Transactional
public class LocationServiceImpl implements ILocationService {
	
	IGenericDao<Location> dao;
	
	//Ustawienie klasy, na ktorej ma operowac DAO
	   @Autowired
	   public void setDao(IGenericDao<Location> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(Location.class);
	   }

	//Implementacja dodawania lokacji do tabeli   
	@Override
	public void createLocation(Location data) {
		dao.create(data);
	}

	//Implementacja wyszukiwania wszystkich lokacji
	@Override
	public List<Location> findAllLocations() {
		return dao.findAll();
	}

	//Implementacja wyszukiwania lokacji po id
	@Override
	public Location findLocationById(long id) {
		return dao.findOneById(id);
	}

	//Implementacja aktualizacji lokacji
	@Override
	public void updateLocation(Location data) {
		dao.update(data);
	}

	//Implementacja usuwania lokacji po id
	@Override
	public void deleteLocationById(long id) {
		dao.deleteById(id);
	}	
}
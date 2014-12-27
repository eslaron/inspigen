package org.sobiech.inspigen.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.core.models.entities.Location;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.services.ILocationService;

@Service
@Transactional
public class LocationServiceImpl implements ILocationService {
	
	IGenericDao<Location> dao;
	 
	   @Autowired
	   public void setDao(IGenericDao<Location> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(Location.class);
	   }

	@Override
	public void createLocation(Location data) {
		dao.create(data);
	}

	@Override
	public List<Location> findAllLocations() {
		return dao.findAll();
	}

	@Override
	public Location findLocationById(long id) {
		return dao.findOneById(id);
	}

	@Override
	public void updateLocation(Location data) {
		dao.update(data);
	}

	@Override
	public void deleteLocationById(long id) {
		dao.deleteById(id);
	}	
}
package org.sobiech.inspigen.core.services;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Location;

public interface ILocationService {
	
	public void createLocation(Location data);
	
	public List<Location> findAllLocations();
	
	public Location findLocationById(long id);
		
	public void updateLocation(Location data);
	
	public void deleteLocationById(long id);	
}
package org.sobiech.inspigen.core.services;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Location;

//Interfejs zawierajacy prototypy metod operujących na klasie Location
public interface ILocationService {
	
	public void createLocation(Location data);		//Dodawanie lokacji do tabeli
	
	public List<Location> findAllLocations();		//Wyszukiwanie wszystkich lokacji
	
	public Location findLocationById(long id);		//Wyszukiwanie lokacji po id
		
	public void updateLocation(Location data);		//Aktualizacja lokacji
	
	public void deleteLocationById(long id);		//Usuwanie lokacji po id
}
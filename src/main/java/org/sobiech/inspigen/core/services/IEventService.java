package org.sobiech.inspigen.core.services;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Event;

//Interfejs zawierajacy prototypy metod operujących na klasie Event
public interface IEventService {
	
	public void createEvent(Event data);		//Dodawnie wydarzenia do tabeli
	
	public List<Event> findAllEvents();			//Wyszukiwanie wszystkich wydarzeń
	
	public Event findEventById(long id);		//Wyszukiwanie wydarzenia po id
		
	public void updateEvent(Event data);		//Aktualizacja wydarzenia
	
	public void deleteEventById(long id);		//Usuwanie wydarzenia po id
}
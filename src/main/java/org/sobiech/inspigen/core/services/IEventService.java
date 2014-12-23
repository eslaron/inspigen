package org.sobiech.inspigen.core.services;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Event;


public interface IEventService {
	
	public void createEvent(Event data);
	
	public List<Event> findAllEvents();
	
	public Event findEventById(long id);
		
	public void updateEvent(Event data);
	
	public void deleteEventById(long id);	
}
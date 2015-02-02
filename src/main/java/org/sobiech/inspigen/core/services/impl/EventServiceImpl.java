package org.sobiech.inspigen.core.services.impl;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Event;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.services.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Klasa implementujaca interfejs IEventService
@Service
@Transactional
public class EventServiceImpl implements IEventService {

	IGenericDao<Event> dao;
	 
	//Ustawienie klasy, na ktorej ma operowac DAO
	   @Autowired
	   public void setDao(IGenericDao<Event> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(Event.class);
	   }

	//Implementacja dodawania wydarzenia do tabeli   
	@Override
	public void createEvent(Event data) {
		dao.create(data);	
	}

	//Implementacja wyszukiwania wszystkich wydarzeń
	@Override
	public List<Event> findAllEvents() {
		return dao.findAll();
	}

	//Implementacja wyszukiwania wydarzenia po id
	@Override
	public Event findEventById(long id) {
		return dao.findOneById(id);
	}

	//Implementacja aktualizacji wydarzenia
	@Override
	public void updateEvent(Event data) {
		dao.update(data);
	}

	//Implementacja usuwania wydarzenia po id
	@Override
	public void deleteEventById(long id) {
		dao.deleteById(id);
	}	
}
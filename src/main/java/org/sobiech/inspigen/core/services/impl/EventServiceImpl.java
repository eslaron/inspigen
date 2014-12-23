package org.sobiech.inspigen.core.services.impl;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Event;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.services.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class EventServiceImpl implements IEventService {

	IGenericDao<Event> dao;
	 
	   @Autowired
	   public void setDao(IGenericDao<Event> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(Event.class);
	   }

	@Override
	public void createEvent(Event data) {
		dao.create(data);	
	}

	@Override
	public List<Event> findAllEvents() {
		return dao.findAll();
	}

	@Override
	public Event findEventById(long id) {
		return dao.findOneById(id);
	}

	@Override
	public void updateEvent(Event data) {
		dao.update(data);
	}

	@Override
	public void deleteEventById(long id) {
		dao.deleteById(id);
	}	
}
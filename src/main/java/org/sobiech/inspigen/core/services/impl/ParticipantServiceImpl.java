package org.sobiech.inspigen.core.services.impl;

import java.util.List;
import org.sobiech.inspigen.core.models.entity.Participant;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.services.IParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Klasa implementujaca interfejs IParticipantService
@Service
@Transactional
public class ParticipantServiceImpl implements IParticipantService {

	IGenericDao<Participant> dao;
	 
	//Ustawienie klasy, na ktorej ma operowac DAO
	   @Autowired
	   public void setDao(IGenericDao<Participant> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(Participant.class);
	   }

	//Implementacja dodawania uczestnika do tabeli   
	@Override
	public void createParticipant(Participant data) {
		dao.create(data);
	}

	//Implementacja wyszukiwania wszystkich uczestnikow
	@Override
	public List<Participant> findAllParticipants() {
		return dao.findAll();
	}

	//Implementacja wyszukiwania uczestnika po id
	@Override
	public Participant findParticipantById(long id) {
		return dao.findOneById(id);
	}

	//Implementacja aktualizacji uczestnika
	@Override
	public void updateParticipant(Participant data) {
		dao.update(data);
	}

	//Implementacja usuwania uczestnika po id
	@Override
	public void deleteParticipantById(long id) {
		dao.deleteById(id);
	}	
}
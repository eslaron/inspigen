package org.sobiech.inspigen.core.services.impl;

import java.util.List;
import org.sobiech.inspigen.core.models.entities.Participant;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.services.IParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ParticipantServiceImpl implements IParticipantService {

	IGenericDao<Participant> dao;
	 
	   @Autowired
	   public void setDao(IGenericDao<Participant> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(Participant.class);
	   }

	@Override
	public void createParticipant(Participant data) {
		dao.create(data);
	}

	@Override
	public List<Participant> findAllParticipants() {
		return dao.findAll();
	}

	@Override
	public Participant findParticipantById(long id) {
		return dao.findOneById(id);
	}

	@Override
	public void updateParticipant(Participant data) {
		dao.update(data);
	}

	@Override
	public void deleteParticipantById(long id) {
		dao.deleteById(id);
	}	
}
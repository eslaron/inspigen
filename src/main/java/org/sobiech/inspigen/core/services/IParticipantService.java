package org.sobiech.inspigen.core.services;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Participant;

public interface IParticipantService {
	
	public void createParticipant(Participant data);
	
	public List<Participant> findAllParticipants();
	
	public Participant findParticipantById(long id);
		
	public void updateParticipant(Participant data);
	
	public void deleteParticipantById(long id);	
}
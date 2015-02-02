package org.sobiech.inspigen.core.services;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Participant;

//Interfejs zawierajacy prototypy metod operujących na klasie Participant
public interface IParticipantService {
	
	public void createParticipant(Participant data);		//Dodawanie uczestnika do tabeli
	
	public List<Participant> findAllParticipants();			//Wyszukiwanie wszystkich uczestnikow
	
	public Participant findParticipantById(long id);		//Wyszukiwanie uczestnika po id
		
	public void updateParticipant(Participant data);		//Aktualizacja uczestnika
	
	public void deleteParticipantById(long id);				//Usuwanie uczestnika po id
}
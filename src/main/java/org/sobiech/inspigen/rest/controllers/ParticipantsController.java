package org.sobiech.inspigen.rest.controllers;

import java.util.List;
import org.sobiech.inspigen.core.models.entities.Participant;
import org.sobiech.inspigen.core.services.IParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

@RestController								//Oznaczenie jako kontroler typu REST
@RequestMapping("/api/v1/participants")		//Mapowanie zasobu
public class ParticipantsController {
	
	String message = "";	

	@Autowired
	IParticipantService participantService;

	//Ządanie POST dodające uczestnika do tabeli
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody Participant data) {
    	
    	message = "participantCreated";
    	HttpStatus responseStatus = HttpStatus.CREATED;
    
    	participantService.createParticipant(data);
    	JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", message);
		return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie GET zwracające wszystkich uczestnikow
    @RequestMapping(method = RequestMethod.GET)
    public List<Participant> findAllParticipants(){
       return participantService.findAllParticipants();
    }
    
    //Ządanie GET zwracajace uczestnika po id
    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public Participant findParticipantById(@PathVariable int id){
       return participantService.findParticipantById(id);
    }
       
    //Ządanie PUT aktualizujące uczestnika
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateParticipant(@RequestBody Participant data) {
    	
    	message = "participantUpdated";
    	HttpStatus responseStatus = HttpStatus.OK;

    	participantService.updateParticipant(data);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie DELETE usuwające uczestnika po id
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteParticipantById(@PathVariable long id) {
    	
    	message = "participantDeleted";
    	HttpStatus responseStatus = HttpStatus.OK;
 	
    	participantService.deleteParticipantById(id);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}
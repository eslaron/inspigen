package org.sobiech.inspigen.rest.controllers;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Event;
import org.sobiech.inspigen.core.services.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

@RestController
@RequestMapping("/api/v1/events")
public class EventsController {
	
	String message = "";	

	@Autowired
	IEventService eventService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody Event data) {
    	
    	message = "eventCreated";
    	HttpStatus responseStatus = HttpStatus.OK;
    
    	eventService.createEvent(data);
    	JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", message);
		return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Event> findAllEvents(){
       return eventService.findAllEvents();
    }
    
    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public Event findEventById(@PathVariable int id){
       return eventService.findEventById(id);
    }
        
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateEvent(@RequestBody Event data) {
    	
    	message = "participantUpdated";
    	HttpStatus responseStatus = HttpStatus.OK;

    	eventService.updateEvent(data);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteEventById(@PathVariable long id) {
    	
    	message = "eventDeleted";
    	HttpStatus responseStatus = HttpStatus.OK;
 	
    	eventService.deleteEventById(id);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}
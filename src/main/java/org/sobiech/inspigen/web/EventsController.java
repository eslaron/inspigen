package org.sobiech.inspigen.web;

import java.util.List;

import org.sobiech.inspigen.core.models.entity.Event;
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

@RestController							//Oznaczenie jako kontroler typu REST
@RequestMapping("/api/v1/events")		//Mapowanie zasobu
public class EventsController {
	
	String message = "";	

	@Autowired
	IEventService eventService;

	//Ządanie POST dodające nowe wydarzenie do tabeli
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody Event data) {
    	
    	message = "eventCreated";
    	HttpStatus responseStatus = HttpStatus.CREATED;
    
    	eventService.createEvent(data);
    	JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", message);
		return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie GET zwracające wszystkie wydarzenia
    @RequestMapping(method = RequestMethod.GET)
    public List<Event> findAllEvents(){
       return eventService.findAllEvents();
    }
    
    //Ządanie GET zwracajace wydarzenie po id
    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public Event findEventById(@PathVariable int id){
       return eventService.findEventById(id);
    }
     
    //Ządanie PUT aktualizujące wydarzenie
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateEvent(@RequestBody Event data) {
    	
    	message = "participantUpdated";
    	HttpStatus responseStatus = HttpStatus.OK;

    	eventService.updateEvent(data);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie DELETE usuwające wydarzenie po id
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
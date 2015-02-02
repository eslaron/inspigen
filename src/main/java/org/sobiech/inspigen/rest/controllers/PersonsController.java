package org.sobiech.inspigen.rest.controllers;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Person;
import org.sobiech.inspigen.core.services.IPersonService;
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
@RequestMapping("/api/v1/persons")		//Mapowanie zasobu
public class PersonsController {
	
	String message = "";	

	@Autowired
	IPersonService personService;

	//Ządanie POST dodające nowe dane osobowe do tabeli
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody Person data) {

    	message = "personCreated";
    	HttpStatus responseStatus = HttpStatus.CREATED;
    
    	personService.createPerson(data);
    	
    	JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", message);
		return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie GET zwracające wszystkie dane osobowe
    @RequestMapping(method = RequestMethod.GET)
    public List<Person> findAllPersons(){
       return personService.findAllPersons();
    }
    
    //Ządanie PUT aktualizujące dane osobowe
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updatePerson(@RequestBody Person data) {
    	
    	message = "personUpdated";
    	HttpStatus responseStatus = HttpStatus.OK;

    	personService.updatePerson(data);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie DELETE usuwające dane osobowe
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletePerson(@PathVariable long id) {
    	
    	message = "personDeleted";
    	HttpStatus responseStatus = HttpStatus.OK;
 	
    	personService.deletePersonById(id);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}
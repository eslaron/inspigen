package com.devrebel.inspigen.app.domain.person;

import java.util.List;

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
	PersonRepository repository;

	//Ządanie POST dodające nowe dane osobowe do tabeli
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody Person data) {

    	message = "personCreated";
    	HttpStatus responseStatus = HttpStatus.CREATED;
    
    	repository.save(data);
    	
    	JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", message);
		return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie GET zwracające wszystkie dane osobowe
    @RequestMapping(method = RequestMethod.GET)
    public List<Person> findAllPersons(){
       return repository.findAll();
    }
    
    //Ządanie PUT aktualizujące dane osobowe
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updatePerson(@RequestBody Person data) {
    	
    	message = "personUpdated";
    	HttpStatus responseStatus = HttpStatus.OK;

    	repository.saveAndFlush(data);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie DELETE usuwające dane osobowe
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletePerson(@PathVariable long id) {
    	
    	message = "personDeleted";
    	HttpStatus responseStatus = HttpStatus.OK;
 	
    	repository.delete(id);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}
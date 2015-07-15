package com.devrebel.inspigen.app.domain.location;

import java.util.List;

import com.devrebel.inspigen.core.service.SimpleService;
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
@RequestMapping("/api/v1/locations")	//Mapowanie zasobu
public class LocationsController {
	
	String message = "";	

	@Autowired
	SimpleService<Long,Location> locationService;

	//Ządanie POST dodające nową lokację do tabeli
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody Location data) {
    	
    	message = "locationCreated";
    	HttpStatus responseStatus = HttpStatus.CREATED;
    
    	locationService.create(data);
    	JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", message);
		return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie GET zwracające wszystkie lokacje
    @RequestMapping(method = RequestMethod.GET)
    public List<Location> findAllLocations(){
       return locationService.findAll();
    }
    
    //Ządanie GET zwracające lokację po id
    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public Location findLocationById(@PathVariable long id){
       return locationService.findById(id);
    }
    
    //Ządanie PUT aktualizujące lokację po id
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateLocation(@RequestBody Location data) {
    	
    	message = "locationUpdated";
    	HttpStatus responseStatus = HttpStatus.OK;

    	locationService.update(data);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie DELETE usuwające lokację po id
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteLocationById(@PathVariable long id) {
    	
    	message = "locationDeleted";
    	HttpStatus responseStatus = HttpStatus.OK;
 	
    	locationService.deleteById(id);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}
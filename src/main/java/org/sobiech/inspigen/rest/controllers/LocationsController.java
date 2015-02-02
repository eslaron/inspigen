package org.sobiech.inspigen.rest.controllers;

import java.util.List;
import org.sobiech.inspigen.core.models.entities.Location;
import org.sobiech.inspigen.core.services.ILocationService;
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
	ILocationService locationService;

	//Ządanie POST dodające nową lokację do tabeli
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody Location data) {
    	
    	message = "locationCreated";
    	HttpStatus responseStatus = HttpStatus.CREATED;
    
    	locationService.createLocation(data);
    	JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", message);
		return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie GET zwracające wszystkie lokacje
    @RequestMapping(method = RequestMethod.GET)
    public List<Location> findAllLocations(){
       return locationService.findAllLocations();
    }
    
    //Ządanie GET zwracające lokację po id
    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public Location findLocationById(@PathVariable int id){
       return locationService.findLocationById(id);
    }
    
    //Ządanie PUT aktualizujące lokację po id
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateLocation(@RequestBody Location data) {
    	
    	message = "locationUpdated";
    	HttpStatus responseStatus = HttpStatus.OK;

    	locationService.updateLocation(data);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie DELETE usuwające lokację po id
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteLocationById(@PathVariable long id) {
    	
    	message = "locationDeleted";
    	HttpStatus responseStatus = HttpStatus.OK;
 	
    	locationService.deleteLocationById(id);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}
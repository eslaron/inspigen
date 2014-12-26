package org.sobiech.inspigen.rest.controllers;

import java.util.List;
import org.sobiech.inspigen.core.models.entities.School;
import org.sobiech.inspigen.core.services.ISchoolService;
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
@RequestMapping("/api/v1/schools")
public class SchoolsController {
	
	String message = "";	

	@Autowired
	ISchoolService schoolService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody School data) {
    	
    	message = "schoolCreated";
    	HttpStatus responseStatus = HttpStatus.OK;
    
    	schoolService.createSchool(data);
    	JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", message);
		return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    @RequestMapping(method = RequestMethod.GET)
    public List<School> findAllSchools(){
       return schoolService.findAllSchools();
    }
    
    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public School findSchoolById(@PathVariable int id){
       return schoolService.findSchoolById(id);
    }
        
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateSchool(@RequestBody School data) {
    	
    	message = "schoolUpdated";
    	HttpStatus responseStatus = HttpStatus.OK;

    	schoolService.updateSchool(data);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteSchoolById(@PathVariable long id) {
    	
    	message = "schoolDeleted";
    	HttpStatus responseStatus = HttpStatus.OK;
 	
    	schoolService.deleteSchoolById(id);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}
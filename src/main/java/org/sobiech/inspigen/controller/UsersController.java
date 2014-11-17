package org.sobiech.inspigen.controller;


import org.sobiech.inspigen.model.User;
import org.sobiech.inspigen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

	@Autowired
	UserService userService;
	
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody User user) {

    	HttpStatus responseStatus = HttpStatus.OK;
    	JsonObject jsonResponse = new JsonObject();
    	
		try {
			
			boolean userNameFound = false;
			boolean emailFound =  false;
				
		    if (userService.getUserByName(user.getUsername()) != null) {
		    	userNameFound = true;
		    	responseStatus = HttpStatus.CONFLICT;
		    	jsonResponse.addProperty("id", "Resource Conflict");
				jsonResponse.addProperty("description", "duplicateUser");
		    } 	
	        			    		    
		    if (userService.getUserByEmail(user.getEmail()) != null) {
		    	emailFound = true;
		    	responseStatus = HttpStatus.CONFLICT;
		    	jsonResponse.addProperty("id", "Resource Conflict");
				jsonResponse.addProperty("description", "duplicateEmail");
		    }	
		    
		    if(userNameFound == true && emailFound == true) {
		    	responseStatus = HttpStatus.CONFLICT;
		    	jsonResponse.addProperty("id", "Resource Conflict");
				jsonResponse.addProperty("description", "duplicateUser&Email");
		    }
		    	
		    if(userNameFound == false && emailFound == false) {
		    	userService.addUser(user);
		    	jsonResponse.addProperty("message", "Create Success");
		    }
		} 
		    
		catch (ConstraintViolationException e) {
		
			jsonResponse.addProperty("id", "Creation Failed");
			jsonResponse.addProperty("description", e.getConstraintViolations().iterator().next().getMessage());
			
			return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.PRECONDITION_FAILED);
		} 
		
    	return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable int userId) {

		try {
			userService.getUserById(userId);
		} 
		catch (ConstraintViolationException e) {
	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("id", "Creation Failed");
			jsonResponse.addProperty("description", e.getConstraintViolations().iterator().next().getMessage());
			
			return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.NOT_FOUND);
		} 
		
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", "Create Success");
		
    	return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<String> getByUsername(@PathVariable String username) {

		try {
			userService.getUserByName(username);
		} 
		catch (ConstraintViolationException e) {
	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("id", "Creation Failed");
			jsonResponse.addProperty("description", e.getConstraintViolations().iterator().next().getMessage());
			
			return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.NOT_FOUND);
		} 
		
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", "Creation Success");
		
    	return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public ResponseEntity<String> getByEmail(@PathVariable String email) {

		try {
			userService.getUserByEmail(email);
		} 
		catch (ConstraintViolationException e) {
	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("id", "Creation Failed");
			jsonResponse.addProperty("description", e.getConstraintViolations().iterator().next().getMessage());
			
			return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.NOT_FOUND);
		} 
		
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", "Creation Success");
		
    	return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.OK);
    }
}

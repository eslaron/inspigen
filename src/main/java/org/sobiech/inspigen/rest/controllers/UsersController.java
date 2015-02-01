package org.sobiech.inspigen.rest.controllers;

import java.util.List;

import org.sobiech.inspigen.core.models.dto.UserDto;
import org.sobiech.inspigen.core.services.IEmailService;
import org.sobiech.inspigen.core.services.IUserService;
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
@RequestMapping("/api/v1/users")
public class UsersController {
	
	String message = "";	

	@Autowired
	IUserService userService;
	
	@Autowired
	IEmailService emailService;
	
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody UserDto data) {
    	
    	return userService.addUser(data);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> findAll(){
       return userService.findAllUsers();
    }
        
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUser(@RequestBody UserDto data) {
    	
    	message = "userUpdated";
    	HttpStatus responseStatus = HttpStatus.OK;

    	userService.updateUser(data);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@PathVariable long id) {
    	
    	message = "userDeleted";
    	HttpStatus responseStatus = HttpStatus.OK;
 	
    	userService.deleteUserById(id);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}
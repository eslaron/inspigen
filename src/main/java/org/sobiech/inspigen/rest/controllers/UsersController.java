package org.sobiech.inspigen.rest.controllers;

import java.util.List;

import org.sobiech.inspigen.core.models.DTO.UserDTO;
import org.sobiech.inspigen.core.models.entities.User;
import org.sobiech.inspigen.core.services.EmailService;
import org.sobiech.inspigen.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
	
	String message = "";	

	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;
	

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody User data) {

    	return userService.addUser(data);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<UserDTO> findAll(){
       return userService.findAllUsers();
    }
    
    @RequestMapping(params = {"page", "size"}, method = RequestMethod.GET)
    public List<User> findPage(@RequestParam("page") int page, @RequestParam("size") int size){
       return userService.findPage(page, size);
    }
    
    @RequestMapping(value="/firstPage", params = {"size"}, method = RequestMethod.GET)
    public List<User> findFirstPage(@RequestParam("size") int size){

       return userService.findFirstPage(size);
    }
 
    @RequestMapping(value="/lastPage", params = {"size"}, method = RequestMethod.GET)
    public List<User> findLastPage(@RequestParam("size") int size){

       return userService.findLastPage(size);
    }
    
    @RequestMapping(value="/entitySize", method = RequestMethod.GET)
    public Long entitySize(){
       return userService.entitySize();
    }
    
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUser(@RequestBody UserDTO data) {
    	
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

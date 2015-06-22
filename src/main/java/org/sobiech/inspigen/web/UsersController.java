package org.sobiech.inspigen.web;

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

@RestController						//Oznaczenie jako kontroler typu REST
@RequestMapping("/api/v1/users")	//Mapowanie zasobu
public class UsersController {
	
	String message = "";	

	@Autowired
	IUserService userService;
	
	@Autowired
	IEmailService emailService;
	
	//Ządanie POST dodające użytkownika do tabeli
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody UserDto data) {
    	
    	return userService.addUser(data);
    }
    
    //Ządanie GET zwracajace wszystkich użytkownikow
    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> findAll(){
       return userService.findAllUsers();
    }
      
    //Ządanie PUT aktualizujące użytkownika
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUser(@RequestBody UserDto data) {
    	
    	message = "userUpdated";
    	HttpStatus responseStatus = HttpStatus.OK;

    	userService.updateUser(data);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie DELETE usuwające użytkownika po id
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserById(@PathVariable long id) {
    	
    	message = "userDeleted";
    	HttpStatus responseStatus = HttpStatus.OK;
 	
    	userService.deleteUserById(id);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}
package org.sobiech.inspigen.controller;


import org.sobiech.inspigen.model.User;
import org.sobiech.inspigen.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.JsonObject;


@RestController
@RequestMapping("/api/v1/roles")
public class RolesController {
	
	@Autowired
	RoleService roleService;

	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody User user) {

		roleService.addRole(user);
		
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", "Create Success");
		
    	return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.CREATED);
    }
}

package org.sobiech.inspigen.rest.controllers;

import java.util.List;
import org.sobiech.inspigen.core.models.entities.Address;
import org.sobiech.inspigen.core.services.IAddressService;
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
@RequestMapping("/api/v1/addresses")
public class AddressesController {
	
	String message = "";	

	@Autowired
	IAddressService addressService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody Address data) {
    	
    	message = "addressCreated";
    	HttpStatus responseStatus = HttpStatus.OK;
    
    	addressService.createAddress(data);
    	JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", message);
		return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Address> findAllAddresses(){
       return addressService.findAllAddresses();
    }
    
    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public Address findAddressById(@PathVariable int id){
       return addressService.findAddressById(id);
    }
        
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateAddress(@RequestBody Address data) {
    	
    	message = "addressUpdated";
    	HttpStatus responseStatus = HttpStatus.OK;

    	addressService.updateAddress(data);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAddressById(@PathVariable long id) {
    	
    	message = "addressDeleted";
    	HttpStatus responseStatus = HttpStatus.OK;
 	
    	addressService.deleteAddressById(id);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}
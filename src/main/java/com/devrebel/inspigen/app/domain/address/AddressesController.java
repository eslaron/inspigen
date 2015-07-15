package com.devrebel.inspigen.app.domain.address;

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
@RequestMapping("/api/v1/addresses")	//Mapowanie zasobu
public class AddressesController {
	
	String message = "";	

	@Autowired
	SimpleService<Long,Address> addressService;

	//Ządanie POSt dodające nowy adres
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody Address data) {
    	
    	message = "addressCreated";
    	
    	//Status odpowiedzi
    	HttpStatus responseStatus = HttpStatus.CREATED;
    
    	addressService.create(data);
    	JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", message);
		
		//Wyślij odpowiedź zawierajacą status i komunikat JSON
		return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie GET zwracajace wszystkie adresy
    @RequestMapping(method = RequestMethod.GET)
    public List<Address> findAllAddresses(){
       return addressService.findAll();
    }
    
    //Ządanie GET zwracające jeden adres po podanym id
    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public Address findAddressById(@PathVariable long id){
       return addressService.findById(id);
    }
    
    //Ządanie PUT aktualizujące adres
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateAddress(@RequestBody Address data) {
    	
    	message = "addressUpdated";
    	
    	//Status odpowiedzi
    	HttpStatus responseStatus = HttpStatus.OK;

    	addressService.update(data);
    	
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", message);
			
		//Wyślij odpowiedź zawierajacą status i komunikat JSON
		return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie DELETE usuwajace adres po id
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAddressById(@PathVariable long id) {
    	
    	message = "addressDeleted";
    	
    	//Status odpowiedzi
    	HttpStatus responseStatus = HttpStatus.OK;
 	
    	addressService.deleteById(id);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			
			//Wyślij odpowiedź zawierajacą status i komunikat JSON
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}
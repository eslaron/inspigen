package org.sobiech.inspigen.rest.controllers;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Settings;
import org.sobiech.inspigen.core.services.ISettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

@RestController
@RequestMapping("/api/v1/settings")
public class SettingsController {
	
	String message = "";	

	@Autowired
	ISettingsService settingsService;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Settings> getSettings() {
       return settingsService.getSettings();
    }
        
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateSettingsById(@RequestBody Settings data) {
    	
    	message = "settingsUpdated";
    	HttpStatus responseStatus = HttpStatus.OK;

    	settingsService.updateSettings(data);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}	
}
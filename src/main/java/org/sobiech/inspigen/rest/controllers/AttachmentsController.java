package org.sobiech.inspigen.rest.controllers;


import java.util.List;

import org.sobiech.inspigen.core.models.dto.AttachmentDto;
import org.sobiech.inspigen.core.models.entities.Attachment;
import org.sobiech.inspigen.core.services.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

@RestController								//Oznaczenie jako kontroler typu REST
@RequestMapping("/api/v1/attachments")		//Mapowanie zasobu
public class AttachmentsController {
	
	String message = "";	

	@Autowired
	IAttachmentService attachmentService;

	//Ządanie POST dodające nowy załącznik
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody Attachment file) {
  	
    	message = "attachmentCreated";
    	
    	//Status odpowiedzi
    	HttpStatus responseStatus = HttpStatus.CREATED;
 	
		    	Attachment attachment = new Attachment();
		    	attachment.setFileName(file.getFileName());
		    	attachment.setFileType(file.getFileType());
		    	
		    	if(file.getEvent_id() != 0)
		    		attachment.setEvent_id(file.getEvent_id());
		    	
		    	attachment.setBlobUrl(file.getBlobUrl());
		    	attachment.setUser_id(file.getUser_id());
		    	attachment.setFile(file.getFile());
	    	
		    	attachmentService.createAttachment(attachment);
      
    	JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", message);
		
		//Wyślij odpowiedź zawierajacą status i komunikat JSON
		return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie GET zwracające wszystkie załączniki
    @RequestMapping(method = RequestMethod.GET)
    public List<Attachment> findAllAttachments(){
       return attachmentService.findAllAttachments();
    }
    
    //Ządanie GET zwracające załącznik pod id
    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public AttachmentDto findAttachmentById(@PathVariable int id) {
    	return attachmentService.findAttachmentById(id);   
    }
    
    //Ządanie GET zwracające załącznik po id użytkownika
    @RequestMapping(value ="/user/{id}", method = RequestMethod.GET)
    public AttachmentDto findPhotoAttachmentByUserId(@PathVariable int id) {
    	return attachmentService.findPhotoAttachmentByUserId(id);   
    }
    
    //Ządanie GET zwracające załączniki po id wydarzenia
    @RequestMapping(value ="/event/{id}", method = RequestMethod.GET)
    public List<AttachmentDto> findAttachmentsByEventId(@PathVariable int id) {   
    	return attachmentService.findAttachmentsByEventId(id);
    }
    
    //Ządanie PUT aktualizujące załącznik po id użytkownika
    @RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<String> updateAttachmentByUserId(@RequestBody Attachment data) {
    	
    	message = "attachmentUpdated";
    	HttpStatus responseStatus = HttpStatus.OK;

    	attachmentService.updateAttachmentByUserId(data);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie DELETE usuwające załącznik po id
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
   	public ResponseEntity<String> deleteAttachmentById(@PathVariable int id) {
       	
       	message = "attachmentDeleted";
       	HttpStatus responseStatus = HttpStatus.OK;
    	
       	attachmentService.deleteAttachmentById(id);
       	
   			JsonObject jsonResponse = new JsonObject();
   			jsonResponse.addProperty("message", message);
   			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
   	}
    
    //Zadanie DELETE usuwające załącznik po id użytkownika
    @RequestMapping(value ="/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAttachmentByUserId(@PathVariable int id) {
    	
    	message = "attachmentDeleted";
    	HttpStatus responseStatus = HttpStatus.OK;
 	
    	attachmentService.deleteAttachmentByUserId(id);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}
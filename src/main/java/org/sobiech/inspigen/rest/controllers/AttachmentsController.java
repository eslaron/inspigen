package org.sobiech.inspigen.rest.controllers;


import java.util.List;

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

@RestController
@RequestMapping("/api/v1/attachments")
public class AttachmentsController {
	
	String message = "";	
	String fileName = "";
	String fileType = "";
	int userId = 0;

	@Autowired
	IAttachmentService attachmentService;

	@RequestMapping(value="/fileInfo", method = RequestMethod.POST)
    public ResponseEntity<String> getFileInfo(@RequestBody Attachment data) {
  	
    	message = "fileInfoReceived";
    	HttpStatus responseStatus = HttpStatus.OK;
 	   	    	
    	fileName = data.getFileName();
    	fileType = data.getFileType();
    	userId = data.getUser_id();
    	
    	JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", message);
		return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
	
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody byte[] file) {
  	
    	message = "attachmentCreated";
    	HttpStatus responseStatus = HttpStatus.OK;
 	
		    	Attachment attachment = new Attachment();
		    	attachment.setFileName(fileName);
		    	attachment.setFileType(fileType);
		    	attachment.setUser_id(userId);
		    	attachment.setFile(file);
	    	
		    	attachmentService.createAttachment(attachment);
      
    	JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", message);
		return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Attachment> findAllAttachmentes(){
       return attachmentService.findAllAttachments();
    }
    
    @RequestMapping(value ="/{id}", method = RequestMethod.GET, produces = "image/gif;image/jpeg;image/png;application/pdf;application/msword")
    public byte[] findAttachmentById(@PathVariable int id) {
       return attachmentService.findAttachmentByUserId(id).getFile();
    }
            
    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateAttachment(@RequestBody Attachment data) {
    	
    	message = "attachmentUpdated";
    	HttpStatus responseStatus = HttpStatus.OK;

    	attachmentService.updateAttachment(data);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAttachmentById(@PathVariable long id) {
    	
    	message = "attachmentDeleted";
    	HttpStatus responseStatus = HttpStatus.OK;
 	
    	attachmentService.deleteAttachmentById(id);
    	
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}
package org.sobiech.inspigen.core.services;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Attachment;


public interface IAttachmentService {
	
	public void createAttachment(Attachment data);
	
	public List<Attachment> findAllAttachments();
	
	public Attachment findAttachmentById(long id);
	
	public Attachment findAttachmentByUserId(int id);
		
	public void updateAttachment(Attachment data);
	
	public void deleteAttachmentById(long id);	
}
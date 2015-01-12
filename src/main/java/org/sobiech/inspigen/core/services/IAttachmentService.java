package org.sobiech.inspigen.core.services;

import java.util.List;

import org.sobiech.inspigen.core.models.dto.AttachmentDto;
import org.sobiech.inspigen.core.models.entities.Attachment;


public interface IAttachmentService {
	
	public void createAttachment(Attachment data);
	
	public List<Attachment> findAllAttachments();

	public AttachmentDto findAttachmentById(long id);
	
	public AttachmentDto findAttachmentByUserId(int id);
	
	public AttachmentDto findPhotoAttachmentByUserId(int id);
	
	public List<AttachmentDto> findAttachmentsByEventId(int id);
		
	public void updateAttachment(Attachment data);
	
	public void updateAttachmentByUserId(Attachment data);
	
	public void deleteAttachmentById(long id);	
	
	public void deleteAttachmentByUserId(int id);	
}
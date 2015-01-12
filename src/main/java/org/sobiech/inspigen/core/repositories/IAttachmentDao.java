package org.sobiech.inspigen.core.repositories;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Attachment;

public interface IAttachmentDao {
	
	public Attachment findAttachmentByUserId(int id);
	
	public Attachment findPhotoAttachmentByUserId(int id);
	
	public List<Attachment> findAttachmentsbyEventId(int id);
	
	public void updateAttachmentByUserId(Attachment data);
	
	public void deleteAttachmentByUserId(int id);
}
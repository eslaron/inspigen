package org.sobiech.inspigen.core.repositories;

import org.sobiech.inspigen.core.models.entities.Attachment;

public interface IAttachmentDao {
	
	public Attachment findAttachmentByUserId(int id);
	
	public void updateAttachmentByUserId(Attachment data);
	
	public void deleteAttachmentByUserId(int id);
}
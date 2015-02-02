package org.sobiech.inspigen.core.repositories;

import java.util.List;

import org.sobiech.inspigen.core.models.entities.Attachment;

//Interfejs zawierajacy prototypy metod operujących na klasie Attachment
public interface IAttachmentDao {
	
	public Attachment findAttachmentByUserId(int id);				//Wyszukiwanie załącznika po id użytkownika
	
	public Attachment findPhotoAttachmentByUserId(int id);			//Wyszukiwanie zdjęcia użytkownika po jego id
	
	public List<Attachment> findAttachmentsbyEventId(int id);		//Wyszukiwanie załącznikow po id wydarzenia
	
	public void updateAttachmentByUserId(Attachment data);			//Aktualizowanie załącznika po id użytkownika
	
	public void deleteAttachmentByUserId(int id);					//Usuwanie załącznika po id użytkownika
}
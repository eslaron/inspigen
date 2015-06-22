package org.sobiech.inspigen.core.services;

import java.util.List;

import org.sobiech.inspigen.core.models.dto.AttachmentDto;
import org.sobiech.inspigen.core.models.entity.Attachment;

//Interfejs zawierajacy prototypy metod operujących na klasie Attachment
public interface IAttachmentService {
	
	public void createAttachment(Attachment data);					 //Dodawanie załącznika do tabeli
	
	public List<Attachment> findAllAttachments();					 //Wyszukiwanie wszystkich załącznikow w tabeli

	public AttachmentDto findAttachmentById(long id);		         //Wyszukiwanie załącznika pod id
	
	public AttachmentDto findAttachmentByUserId(int id);			 //Wyszukiwanie załacznika po id użytkownika
	
	public AttachmentDto findPhotoAttachmentByUserId(int id);		 //Wyszukiwanie zdjęcia po id użytkownika
	
	public List<AttachmentDto> findAttachmentsByEventId(int id);     //Wyszukiwanie załącznika po id wydarzenia
		
	public void updateAttachment(Attachment data);					 //Aktualizacja załącznika
	
	public void updateAttachmentByUserId(Attachment data);           //Aktualizacja załącznika po id użytkownika
	
	public void deleteAttachmentById(long id);						 //Usuwanie załącznika po id
	
	public void deleteAttachmentByUserId(int id);					 //Usuwanie załącznika po id użytkownika
}
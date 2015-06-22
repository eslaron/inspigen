package org.sobiech.inspigen.core.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.core.models.dto.AttachmentDto;
import org.sobiech.inspigen.core.models.entity.Attachment;
import org.sobiech.inspigen.core.repositories.IAttachmentDao;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.services.IAttachmentService;

//Klasa implementujaca interfejs IAttachmentService
@Service
@Transactional
public class AttachmentServiceImpl implements IAttachmentService {
	
	IGenericDao<Attachment> dao;
	 
	//Ustawienie klasy, na ktorej ma operowac DAO
	   @Autowired
	   public void setDao(IGenericDao<Attachment> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(Attachment.class);
	   }
	   
	   @Autowired
	   IAttachmentDao attachmentDao;

	//Implementacja dodawania załącznika do tabeli  
	@Override
	public void createAttachment(Attachment data) {
		dao.create(data);
	}

	//Implementacja wyszukiwania wszystkich załącznikow w tabeli
	@Override
	public List<Attachment> findAllAttachments() {
		return dao.findAll();
	}
	
	//Implementacja wyszukiwania załącznika po id
	@Override
	public AttachmentDto findAttachmentById(long id) {
		
		//Znajdujemy załącznik
		Attachment attachment = dao.findOneById(id);
		
		//Tworzmy obiekt klasy transportowej
		AttachmentDto attachmentDto = new AttachmentDto();
		
		//Jeśli załącznik nie jest pusty, to przepisujemy zawartość 
		//znalezionego załącznika do obiektu transportowego
		if(attachment != null) {
			
		attachmentDto.setId(attachment.getId());
		attachmentDto.setFileName(attachment.getFileName());
		attachmentDto.setFileType(attachment.getFileType());
		attachmentDto.setFile(new String(attachment.getFile()));
		attachmentDto.setBlobUrl(attachment.getBlobUrl());
		attachmentDto.setUser_id(attachment.getUser_id());
		attachmentDto.setEvent_id(attachment.getEvent_id());
		
		return attachmentDto;
		}		
		else return new AttachmentDto();
	}
	
	//Implementacja wyszukiwania załącznika po id użytkownika
	@Override
	public AttachmentDto findAttachmentByUserId(int id) {
		
		//Znajdujemy załącznik
		Attachment attachment = attachmentDao.findAttachmentByUserId(id);
		
		//Tworzymy nowy obiekt transportowy
		AttachmentDto attachmentDto = new AttachmentDto();
		
		//Jeśli załącznik nie jest pusty, to przepisujemy zawartość 
		//znalezionego załącznika do obiektu transportowego
		if(attachment != null) {
			
		attachmentDto.setId(attachment.getId());
		attachmentDto.setFileName(attachment.getFileName());
		attachmentDto.setFileType(attachment.getFileType());
		attachmentDto.setFile(new String(attachment.getFile()));
		attachmentDto.setBlobUrl(attachment.getBlobUrl());
		attachmentDto.setUser_id(attachment.getUser_id());
		attachmentDto.setEvent_id(attachment.getEvent_id());
		
		return attachmentDto;
		}	
		else return new AttachmentDto();
	}
	

	//Implementacja znajdowania zdjęcia po id użytwkownika
	@Override
	public AttachmentDto findPhotoAttachmentByUserId(int id) {
		
		//Znajdujemy załącznik
		Attachment attachment = attachmentDao.findPhotoAttachmentByUserId(id);
		
		//Tworzymy nowy obiekt transportowy
		AttachmentDto attachmentDto = new AttachmentDto();
		
		//Jeśli załącznik nie jest pusty, to przepisujemy zawartość 
		//znalezionego załącznika do obiektu transportowego
		if(attachment != null) {
			
		attachmentDto.setId(attachment.getId());
		attachmentDto.setFileName(attachment.getFileName());
		attachmentDto.setFileType(attachment.getFileType());
		attachmentDto.setFile(new String(attachment.getFile()));
		attachmentDto.setBlobUrl(attachment.getBlobUrl());
		attachmentDto.setUser_id(attachment.getUser_id());
		attachmentDto.setEvent_id(attachment.getEvent_id());
		
		return attachmentDto;
		}	
		else return new AttachmentDto();
	}
	
	//Implementacja wyszukiwania załącznika po id wydarzenia
	@SuppressWarnings("unused")
	@Override
	public List<AttachmentDto> findAttachmentsByEventId(int id) {
		
		//Znajdujemy załączniki
		List<Attachment> attachments = attachmentDao.findAttachmentsbyEventId(id);
		
		//Tworzymy pustą listę załącznikow
		List<AttachmentDto> emptyAttachmentsList = new ArrayList<AttachmentDto>();
		
		//Tworzymy listę transportową
		List<AttachmentDto> attachmentDtoList = new ArrayList<AttachmentDto>();
		
		//Jeśli lista załącznikow nie jest pusta, to przepisujemy zawartość 
		//listy do listy transportowej
		if(attachments != null) {
			for(Attachment attachment : attachments) {
				
				AttachmentDto attachmentDto = new AttachmentDto();
				
				attachmentDto.setId(attachment.getId());
				attachmentDto.setFileName(attachment.getFileName());
				attachmentDto.setFileType(attachment.getFileType());
				attachmentDto.setFile(new String(attachment.getFile()));
				attachmentDto.setBlobUrl(attachment.getBlobUrl());
				attachmentDto.setUser_id(attachment.getUser_id());
				attachmentDto.setEvent_id(attachment.getEvent_id());
	
				attachmentDtoList.add(attachmentDto);
			}
		}
	
		//Jeśli lista transportowa jest pusta, to zwroć nową, pustą listę
		if(attachmentDtoList == null)
			return emptyAttachmentsList;
		
		else return attachmentDtoList;
	}

	//Implementacja aktualizacji załącznika
	@Override
	public void updateAttachment(Attachment data) {
		dao.update(data);
	}

	//Implementacja aktualizacji załącznika po id użytkownika
	@Override
	public void updateAttachmentByUserId(Attachment data) {
		attachmentDao.updateAttachmentByUserId(data);	
	}
	
	//Implementacja usuwania załącznika po id
	@Override
	public void deleteAttachmentById(long id) {
		dao.deleteById(id);
	}
	
	//Implementacja usuwania załącznika po id użytkownika
	@Override
	public void deleteAttachmentByUserId(int id) {
		attachmentDao.deleteAttachmentByUserId(id);
	}
}
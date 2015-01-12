package org.sobiech.inspigen.core.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.core.models.dto.AttachmentDto;
import org.sobiech.inspigen.core.models.entities.Attachment;
import org.sobiech.inspigen.core.repositories.IAttachmentDao;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.services.IAttachmentService;


@Service
@Transactional
public class AttachmentServiceImpl implements IAttachmentService {
	
	IGenericDao<Attachment> dao;
	 
	   @Autowired
	   public void setDao(IGenericDao<Attachment> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(Attachment.class);
	   }
	   
	   @Autowired
	   IAttachmentDao attachmentDao;

	@Override
	public void createAttachment(Attachment data) {
		dao.create(data);
	}

	@Override
	public List<Attachment> findAllAttachments() {
		return dao.findAll();
	}
	
	@Override
	public AttachmentDto findAttachmentById(long id) {
		
		Attachment attachment = dao.findOneById(id);
		AttachmentDto attachmentDto = new AttachmentDto();
		
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
	
	@Override
	public AttachmentDto findAttachmentByUserId(int id) {
		
		Attachment attachment = attachmentDao.findAttachmentByUserId(id);
		AttachmentDto attachmentDto = new AttachmentDto();
		
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
	

	@Override
	public AttachmentDto findPhotoAttachmentByUserId(int id) {
		
		Attachment attachment = attachmentDao.findPhotoAttachmentByUserId(id);
		AttachmentDto attachmentDto = new AttachmentDto();
		
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
	
	@SuppressWarnings("unused")
	@Override
	public List<AttachmentDto> findAttachmentsByEventId(int id) {
		
		List<Attachment> attachments = attachmentDao.findAttachmentsbyEventId(id);
		List<AttachmentDto> emptyAttachmentsList = new ArrayList<AttachmentDto>();
		List<AttachmentDto> attachmentDtoList = new ArrayList<AttachmentDto>();
		
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
	
		if(attachmentDtoList == null)
			return emptyAttachmentsList;
		
		else return attachmentDtoList;
	}

	@Override
	public void updateAttachment(Attachment data) {
		dao.update(data);
	}

	@Override
	public void updateAttachmentByUserId(Attachment data) {
		attachmentDao.updateAttachmentByUserId(data);	
	}
	
	@Override
	public void deleteAttachmentById(long id) {
		dao.deleteById(id);
	}
	
	@Override
	public void deleteAttachmentByUserId(int id) {
		attachmentDao.deleteAttachmentByUserId(id);
	}
}
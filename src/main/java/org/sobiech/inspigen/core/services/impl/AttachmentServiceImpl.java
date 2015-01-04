package org.sobiech.inspigen.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	public Attachment findAttachmentById(long id) {
		return dao.findOneById(id);
	}

	@Override
	public void updateAttachment(Attachment data) {
		dao.update(data);
	}

	@Override
	public void deleteAttachmentById(long id) {
		dao.deleteById(id);
	}

	@Override
	public Attachment findAttachmentByUserId(int id) {
		return attachmentDao.findAttachmentByUserId(id);
	}	
}
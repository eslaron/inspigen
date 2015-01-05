package org.sobiech.inspigen.core.repositories.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sobiech.inspigen.core.models.entities.Attachment;
import org.sobiech.inspigen.core.repositories.IAttachmentDao;

@Repository
public class AttachmentDaoImpl implements IAttachmentDao {

	@Autowired
	private SessionFactory sessionFactory;
	     
	private Session getCurrentSession() { 
	        return sessionFactory.getCurrentSession();
	}
	
	@Override
	public Attachment findAttachmentByUserId(int id) {
		
		Query query = getCurrentSession().createQuery("from Attachment where user_id = :id ");
		query.setInteger("id", id);
		
		if (query.list().size() == 0 ) {
			return null;
		} else {
			return (Attachment)query.list().get(0);
		}
	}

	@Override
	public void updateAttachmentByUserId(Attachment data) {
		
		Query query = getCurrentSession().createQuery("Update Attachment set fileName = :fn, fileType = :ft, file = :f where user_id = :id");
		query.setString("fn",data.getFileName());
		query.setString("ft",data.getFileType());
		query.setBinary("f", data.getFile());
		query.setInteger("id", data.getUser_id());
		
		query.executeUpdate();
	}

	@Override
	public void deleteAttachmentByUserId(int id) {
		Query query = getCurrentSession().createQuery("Delete Attachment where user_id = :id");
		query.setInteger("id", id);
		
		query.executeUpdate();	
	}
}
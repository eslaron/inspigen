package org.sobiech.inspigen.core.repositories.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sobiech.inspigen.core.models.entities.Attachment;
import org.sobiech.inspigen.core.repositories.IAttachmentDao;

//Klasa implementujaca interfejs IAttachmentDao
@Repository
public class AttachmentDaoImpl implements IAttachmentDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	//Uzyskanie dostępu do sesji
	private Session getCurrentSession() { 
	        return sessionFactory.getCurrentSession();
	}
	
	//Implementacja wyszukiwania załącznika po id użytkownika
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
	
	//Implementacja wyszukiwania zdjęcia po id użytkownika
	@Override
	public Attachment findPhotoAttachmentByUserId(int id) {
		Query query = getCurrentSession().createQuery("from Attachment where user_id = :id and event_id = 0");
		query.setInteger("id", id);
		
		if (query.list().size() == 0 ) {
			return null;
		} else {
			return (Attachment)query.list().get(0);
		}
	}

	//Implementacja wyszukiwania załącznikow po id wydarzenia
	@SuppressWarnings("unchecked")
	@Override
	public List<Attachment> findAttachmentsbyEventId(int id) {
		Query query = getCurrentSession().createQuery("from Attachment where event_id = :id ");
		query.setInteger("id", id);
		
		if (query.list().size() == 0 ) {
			return null;
		} else {
			return query.list();
		}
	}
	
	//Implementacja aktualizacji załącznika po id użytkownika
	@Override
	public void updateAttachmentByUserId(Attachment data) {
		
		Query query = getCurrentSession().createQuery("Update Attachment set fileName = :fn, fileType = :ft, file = :f where user_id = :id");
		query.setString("fn",data.getFileName());
		query.setString("ft",data.getFileType());
		query.setBinary("f", data.getFile());
		query.setInteger("id", data.getUser_id());
		
		query.executeUpdate();
	}

	//Implementacja usuwania załącznika po id użytkownika
	@Override
	public void deleteAttachmentByUserId(int id) {
		Query query = getCurrentSession().createQuery("Delete Attachment where user_id = :id");
		query.setInteger("id", id);
		
		query.executeUpdate();	
	}
}
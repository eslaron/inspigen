package org.sobiech.inspigen.core.repositories.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sobiech.inspigen.core.repositories.IPersonDao;
import org.sobiech.inspigen.core.models.entities.Person;

@Repository
public class PersonDaoImpl implements IPersonDao {

	@Autowired
	private SessionFactory sessionFactory;
	     
	private Session getCurrentSession() { 
	        return sessionFactory.getCurrentSession();
	}
	
	@Override
	public Person findPersonByUserId(long id) {
		
		String getPersonByUserId = "from Person p, User u where p.user_id = :id and p.user_id = u.id";
		Query query = getCurrentSession().createQuery(getPersonByUserId);
		query.setLong("id", id);
		
		if (query.list().size() == 0 ) {
			return null;
		} else {
			return (Person)query.list().get(0);
		}
	}	
}
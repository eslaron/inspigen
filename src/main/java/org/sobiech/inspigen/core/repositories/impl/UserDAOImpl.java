package org.sobiech.inspigen.core.repositories.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sobiech.inspigen.core.repositories.IUserDao;
import org.sobiech.inspigen.core.models.entity.User;

//Klasa implementujaca interfejs IUserDao
@Repository
public class UserDAOImpl implements IUserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	//Uzyskanie dostępu do sesji
	private Session getCurrentSession() { 
	        return sessionFactory.getCurrentSession();
	}
	
	//Implementacja znajdowania użytkownika po nazwie
	@Override
	public User findUserByUsername(String userName) {

		Query query = getCurrentSession().createQuery("from User where username = :userName ");
		query.setString("userName", userName);
		
		if (query.list().size() == 0 ) {
			return null;
		} else {
			return (User)query.list().get(0);
		}
	}
	
	//Implementacja znajdowania użytkownika po emailu
	@Override
	public User findUserByEmail(String email) {

		Query query = getCurrentSession().createQuery("from User where email = :eMail ");
		query.setString("eMail", email);
		
		if (query.list().size() == 0 ) {
			return null;
		} else {
	        return (User)query.list().get(0);
		}
	}

	//Implementacja znajdowania użytkownika po tokenie
	@Override
	public User findUserByToken(String tokenType, String token) {
		Query query = getCurrentSession().createQuery("from User where "+tokenType+" = :token ");
		query.setString("token", token);
		
		if (query.list().size() == 0 ) {
			return null;
		} else {
	        return (User)query.list().get(0);
		}
	}
}
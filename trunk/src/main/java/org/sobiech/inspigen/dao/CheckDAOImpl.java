
package org.sobiech.inspigen.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class CheckDAOImpl implements CheckDAO {
	
    @Autowired
    private SessionFactory sessionFactory;
   
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
	
	// SPRAWDZANIE
		
	@Override
	public Query checkIfUserExists(String username) {
		
		Query query = getCurrentSession().createQuery("from User where username = :usersName ");
		query.setString("usersName", username);
		return query;
	}
	
	@Override
	public Query checkIfEmailIsRegistered(String email) {
		
		Query query = getCurrentSession().createQuery("from User where email = :eMail ");
		query.setString("eMail", email);	
		return query;
	}


	@Override
	public Query checkIfUserIsLocked(String username) {
		
		Query query = getCurrentSession().createQuery("SELECT accountNonLocked from User "
				+ "where accountNonLocked = :locked and username = :userName ");
		query.setBoolean("locked", false);
		query.setString("userName", username);		
		return query;
	}

	
	@Override
	public Query checkIfTokenExists(String tokenType, String token) {
		
		Query query = getCurrentSession().createQuery("from User where "+tokenType+" = :token ");
		query.setString("token", token);
		return query;
	}

	@Override
	public Query checkIfTokenExpired(String tokenType, String token) {
		
		Query query = getCurrentSession().createQuery(
				"SELECT "+tokenType+"Expiration from User where "+tokenType+" = :token ");
		query.setString("token", token);
		return query;
	}


	
}

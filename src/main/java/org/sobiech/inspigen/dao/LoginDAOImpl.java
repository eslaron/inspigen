package org.sobiech.inspigen.dao;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sobiech.inspigen.model.LoginAttempts;

@Repository
public class LoginDAOImpl implements LoginDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Autowired
	LoginAttempts loginAttempts;
   
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
	// PRÓBY LOGOWANIA
	
	@Override
	public void addLoginAttemptEntry(String username) { 
		
	
		loginAttempts.setUsername(username);
		loginAttempts.setAttempts(1);
		loginAttempts.setLastModified(new Date());
		
		getCurrentSession().save(loginAttempts);	
	}
	
	@Override
	public Timestamp getLastAttemptDate(String username) {
	
		Query lastModfied = getCurrentSession().createQuery(
				"SELECT lastModified FROM LoginAttempts WHERE username = :userName");
		lastModfied.setString("userName", username);
			
		return (Timestamp)lastModfied.list().get(0);
	}
	
	@Override
	public LoginAttempts getLoginAttempts(String username) {
	
		try {		
				Query query = getCurrentSession().createQuery(
						"FROM LoginAttempts WHERE username = :userName");
				query.setString("userName", username);
				
				return (LoginAttempts) query.list().get(0);
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}

	@Override
	public void updateLoginFailAttempts(String username) {

		Query updateAttempts = getCurrentSession().createQuery(
				"UPDATE LoginAttempts SET attempts = attempts + 1,"
						+ "lastmodified = :lastModified WHERE username = :userName");
		updateAttempts.setTimestamp("lastModified", new Date());
		updateAttempts.setString("userName", username);
		updateAttempts.executeUpdate();
	}
		
	@Override
	public void resetLoginFailAttempts(String username) {
		
		Query reset = getCurrentSession().createQuery(
				"UPDATE LoginAttempts SET attempts = :reset ,"
				+ "lastmodified = :lastModified WHERE username = :userName");
		
		reset.setInteger("reset", 0);
		reset.setTimestamp("lastModified", new Date());
		reset.setString("userName", username);
		reset.executeUpdate();		
	}
		
	// BLOKOKOWANIE I ODBLOKOWYWANIE KONTA 
	
	@Override
	public void lockAccount(String username) {
		
		Query lock = getCurrentSession().createQuery(
				"UPDATE User SET accountNonLocked = :locked WHERE username = :userName");
		
		lock.setBoolean("locked", false);
		lock.setString("userName", username);
		lock.executeUpdate();
	}

	@Override
	public void unlockAccount(String username) {
		
			Query unlockAccount = getCurrentSession().createQuery(
					"UPDATE User SET accountNonLocked = :locked WHERE username = :userName");
			
			unlockAccount.setBoolean("locked", true);
			unlockAccount.setString("userName", username);
			unlockAccount.executeUpdate();
	}	
}
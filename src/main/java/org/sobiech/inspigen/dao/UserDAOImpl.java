package org.sobiech.inspigen.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sobiech.inspigen.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
    @Autowired
    private SessionFactory sessionFactory;
   
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
	@Override
	public void addUser(User user) {
		getCurrentSession().save(user); 
	}

    @Override
    public User getUserById(int userId) {
   
        User userObject = (User) getCurrentSession().get(User.class, userId);
        
        if (userObject == null) {
        	return null;
        } else {
            return userObject;
        }
    }

	@Override
	public User getUserByName(String usersName) {

		Query query = getCurrentSession().createQuery("from User where username = :usersName ");
		query.setString("usersName", usersName);
		
		if (query.list().size() == 0 ) {
			return null;
		} else {
			return (User)query.list().get(0);
		}
	}
	
	@Override
	public User getUserByEmail(String email) {

		Query query = getCurrentSession().createQuery("from User where email = :eMail ");
		query.setString("eMail", email);
		
		if (query.list().size() == 0 ) {
			return null;
		} else {
	        return (User)query.list().get(0);
		}
	}

	@Override
	public void updateUser(User user) {
		User userToUpdate = getUserById(user.getId());
		userToUpdate.setUsername(user.getUsername());
		userToUpdate.setPassword(user.getPassword());
		userToUpdate.setEnabled(user.getEnabled());
        userToUpdate.setRole(user.getRole());
        getCurrentSession().update(userToUpdate);
	}

	@Override
	public void deleteUser(int userId) {
        User user = getUserById(userId);
        if (user != null) {
            getCurrentSession().delete(user);
        }
	}

	@Override
    @SuppressWarnings("unchecked")
	public List<User> getUsers() {
        return getCurrentSession().createQuery("from User").list();
	}
	
	// TOKEN
	
	@Override
	public String getToken(String tokenType, String email) {
		
		Query getToken = getCurrentSession().createQuery(
				"SELECT "+tokenType+" FROM User WHERE email = :eMail");
		getToken.setString("eMail", email);
		
		return getToken.list().get(0).toString();
	}
	
	@Override
	public void updateToken(String tokenType, String username, String token) {
	    
		    Query setToken = getCurrentSession().createQuery(
					"UPDATE User SET "+tokenType+" = :token WHERE username = :userName");
			
		    setToken.setString("token", token);
		    setToken.setString("userName", username);
		    setToken.executeUpdate();		
	}
	
	// TOKEN - data wyga�ni�cia
	
	@Override
	public Date getTokenExpirationDate(String tokenType, String email) {
		
		Query getDate = getCurrentSession().createQuery(
				"SELECT "+tokenType+"Expiration FROM User WHERE email = :eMail");
		getDate.setString("eMail", email);
		
		return (Date)getDate.list().get(0);
	}
	
	@Override
	public void updateTokenExpirationDate(String tokenType, String email, Date expirationDate) {
				
		Query setToken = getCurrentSession().createQuery(
				"UPDATE User SET "+tokenType+"Expiration = :stamp WHERE email = :eMail");

	    setToken.setTimestamp("stamp", expirationDate);
	    setToken.setString("eMail", email);
	    setToken.executeUpdate();				
	}
}
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
	public void addUser(User user) throws DuplicateUserException {
      
        try {   
	            User userCheck = getUser(user.getUsername());
	            String message = "The user [" + userCheck.getUsername() + "] already exists";
	            throw new DuplicateUserException(message);     
        } catch (UserNotFoundException e) { 
            getCurrentSession().save(user);
        }
	}

    @Override
    public User getUser(int userId) throws UserNotFoundException {
   
        User userObject = (User) getCurrentSession().get(User.class, userId);
        
        if (userObject == null) {
            throw new UserNotFoundException("User id [" + userId + "] not found");
        } else {
            return userObject;
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public User getUser(String usersName) throws UserNotFoundException {

		Query query = getCurrentSession().createQuery("from User where username = :usersName ");
		query.setString("usersName", usersName);
		

		if (query.list().size() == 0 ) {
			throw new UserNotFoundException("User [" + usersName + "] not found");
		} else {
	
			List<User> list = (List<User>)query.list();
	        User userObject = (User) list.get(0);

	        return userObject;
		}
	}

	@Override
	public void updateUser(User user) throws UserNotFoundException {
		User userToUpdate = getUser(user.getId());
		userToUpdate.setUsername(user.getUsername());
		userToUpdate.setPassword(user.getPassword());
		userToUpdate.setEnabled(user.getEnabled());
        userToUpdate.setRole(user.getRole());
        getCurrentSession().update(userToUpdate);
	}

	@Override
	public void deleteUser(int userId) throws UserNotFoundException {
        User user = getUser(userId);
        if (user != null) {
            getCurrentSession().delete(user);
        }
	}

	@Override
    @SuppressWarnings("unchecked")
	public List<User> getUsers() {
        return getCurrentSession().createQuery("from User").list();
	}
	
	// PASSWORD TOKEN
	
	@Override
	public String getPasswordToken(String email) {
		
		Query getToken = getCurrentSession().createQuery(
				"SELECT passwordToken FROM User WHERE email = :eMail");
		getToken.setString("eMail", email);
		
		return getToken.list().get(0).toString();
	}
	
	@Override
	public void updatePasswordToken(String username, String token) {
	    
		    Query setToken = getCurrentSession().createQuery(
					"UPDATE User SET passwordToken = :token WHERE username = :userName");
			
		    setToken.setString("token", token);
		    setToken.setString("userName", username);
		    setToken.executeUpdate();		
	}
	
	// PASSWORD TOKEN - data wygaœniêcia
	
	@Override
	public Date getPasswordTokenExpirationDate(String email) {
		
		Query getDate = getCurrentSession().createQuery(
				"SELECT tokenExpiration FROM User WHERE email = :eMail");
		getDate.setString("eMail", email);
		
		return (Date)getDate.list().get(0);
	}
	
	@Override
	public void updatePasswordTokenExpirationDate(String email, Date expirationDate) {
				
		Query setToken = getCurrentSession().createQuery(
				"UPDATE User SET tokenExpiration = :stamp WHERE email = :eMail");

	    setToken.setTimestamp("stamp", expirationDate);
	    setToken.setString("eMail", email);
	    setToken.executeUpdate();				
	}
}

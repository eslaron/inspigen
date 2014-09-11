package org.sobiech.inspigen.dao;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.sobiech.inspigen.model.LoginAttempts;
import org.sobiech.inspigen.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	

	public static final int LOCK_TIME = 15; //czas blokady u¿ytkownika w minutach
	
	private static int attempts = 3; //domyœlnie 3 podejœcia podawane w ustawieniach
	public static final int MAX_ATTEMPTS = attempts-1; //realna liczba podejœæ
	
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    Md5PasswordEncoder passwordEncoder;
    
    @Autowired 
    ReflectionSaltSource saltSource;
    
      
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
        	
        	String password = user.getPassword();
        	
        	String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(user));
        	
        	user.setPassword(encodedPassword);
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
	
	@Override
	public Boolean checkIfUserExists(String username) {
		
	
		Query query = getCurrentSession().createQuery("from User where username = :usersName ");
		query.setString("usersName", username);
		
		if (query.list().size() == 0 ) {
			return false;
		} else return true;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LoginAttempts getLoginAttempts(String username) {
	
		try {
			
		Query query = getCurrentSession().createQuery(
				"FROM LoginAttempts WHERE username = :userName");
		query.setString("userName", username);
		
		List<LoginAttempts> list = (List<LoginAttempts>)query.list();
		
		LoginAttempts loginAttempts = (LoginAttempts) list.get(0);
		
		return loginAttempts;
		}
		catch(IndexOutOfBoundsException e) {
		
		 return null;
		}
	}

	@Override
	public void updateLoginFailAttempts(String username) {
		
		  LoginAttempts loginAttempts = getLoginAttempts(username);
		  
		  if (checkIfUserExists(username) == true) {
			  
		  if (loginAttempts == null) {
			  	
			  	LoginAttempts login = new LoginAttempts();
				login.setUsername(username);
				login.setAttempts(1);
				login.setLastModified(new Date());
				
				getCurrentSession().save(login);

			}
						
		  	else if (loginAttempts.getAttempts() < MAX_ATTEMPTS) {
					Query updateAttempts = getCurrentSession().createQuery(
							"UPDATE LoginAttempts SET attempts = attempts + 1, "
							+ "lastmodified = :lastModified WHERE username = :userName");
					updateAttempts.setTimestamp("lastModified", new Date());
					updateAttempts.setString("userName", username);
					
					updateAttempts.executeUpdate();

		  		}
		  
		  else  {
			
				Query lock = getCurrentSession().createQuery(
						"UPDATE User SET accountNonLocked = :locked WHERE username = :userName");
				
				lock.setBoolean("locked", false);
				lock.setString("userName", username);
				lock.executeUpdate();
					
			}
		  }
	}
		
	@Override
	public void resetLoginFailAttempts(String username) {
		
		Query reset = getCurrentSession().createQuery(
				"UPDATE LoginAttempts SET attempts = :reset WHERE username = :userName");
		
		reset.setInteger("reset", 0);
		reset.setString("userName", username);
		reset.executeUpdate();		
	}

	@Override
	public void unlockAccount(String username) {
		
		Query lastModfied = getCurrentSession().createQuery(
				"SELECT lastModified FROM LoginAttempts WHERE username = :userName");
		lastModfied.setString("userName", username);
			
		Timestamp stamp = (Timestamp)lastModfied.list().get(0);
		
		Date lastModifiedDate = new Date(stamp.getTime());
		DateFormat format=new SimpleDateFormat("HH:mm:ss");
		format.format(lastModifiedDate);
		
		Calendar cal=Calendar.getInstance();
		cal=format.getCalendar();		
		cal.add(Calendar.MINUTE, LOCK_TIME);
		
		Calendar unlock = cal; 
		
		if(Calendar.getInstance().getTime().after(unlock.getTime()) == true) {
			
			Query unlockAccount = getCurrentSession().createQuery(
					"UPDATE User SET accountNonLocked = :locked WHERE username = :userName");
			
			unlockAccount.setBoolean("locked", true);
			unlockAccount.setString("userName", username);
			unlockAccount.executeUpdate();
		}
	}
}

package org.sobiech.inspigen.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.dao.DuplicateUserException;
import org.sobiech.inspigen.dao.UserDAO;
import org.sobiech.inspigen.dao.UserNotFoundException;
import org.sobiech.inspigen.model.LoginAttempts;
import org.sobiech.inspigen.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
    @Autowired
    private UserDAO userDAO;

	@Override
	public void addUser(User user) throws DuplicateUserException {
		userDAO.addUser(user);
	}

    @Override
    public User getUser(int userId) throws UserNotFoundException {
        return userDAO.getUser(userId);
    }

	@Override
	public User getUser(String username) throws UserNotFoundException {
		return userDAO.getUser(username);
	}

	@Override
	public void updateUser(User user) throws UserNotFoundException {
		userDAO.updateUser(user);
	}

	@Override
	public void deleteUser(int userId) throws UserNotFoundException {
		userDAO.deleteUser(userId);
	}

	@Override
	public List<User> getUsers() {
		return userDAO.getUsers();
	}
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return getUser(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

	@Override
	public Boolean checkIfUserExists(String username) {
		return userDAO.checkIfUserExists(username);
	}
	
	@Override
	public Boolean checkIfEmailIsRegistered(String email) {
		return userDAO.checkIfEmailIsRegistered(email);
	}

	@Override
	public Boolean checkIfPasswordTokenExpired(String token) {
		return userDAO.checkIfPasswordTokenExpired(token);
	}

	@Override
	public void updateLoginFailAttempts(String username) {
		userDAO.updateLoginFailAttempts(username);		
	}

	@Override
	public void resetLoginFailAttempts(String username) {
		userDAO.resetLoginFailAttempts(username);
	}

	@Override
	public LoginAttempts getLoginAttempts(String username) {
		return userDAO.getLoginAttempts(username);
	}

	@Override
	public void unlockAccount(String username) {
		userDAO.unlockAccount(username);
		
	}

	@Override
	public String setPasswordToken() {
		return userDAO.setPasswordToken();
	}

	@Override
	public void updatePasswordToken(String username) {
		userDAO.updatePasswordToken(username);
	}

	@Override
	public String getPasswordToken(String email) {
		return userDAO.getPasswordToken(email);
	}

	@Override
	public Date setPasswordTokenExpirationDate() {
		return userDAO.setPasswordTokenExpirationDate();
	}

	@Override
	public void updatePasswordTokenExpirationDate(String email) {
		userDAO.updatePasswordTokenExpirationDate(email);
	}

	@Override
	public Date getPasswordTokenExpirationDate(String email) {
		return userDAO.getPasswordTokenExpirationDate(email);
	}
}

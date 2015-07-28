package com.devrebel.inspigen.app.domain.user;

import com.devrebel.inspigen.app.domain.account.AccountService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    UserRepository repository;

    @Autowired
    Md5PasswordEncoder passwordEncoder;

    @Autowired 
    ReflectionSaltSource saltSource;

	@Autowired
    AccountService accountService;

	@Override
	public void createUser(User data){

        String password = data.getPassword();
        String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(data));
        String passwordToken = setToken();
        Date passwordTokenExpiration = setTokenExpirationDate();
        String activationToken = setToken();
        Date activationTokenExpiration = setTokenExpirationDate();
        Boolean userEnabled = enableUser(data);
        UserRole assingedRole = assignUserRole(data);

	    User newUser = new User.UserBuilder(data.getUsername(),assingedRole)
             .password(encodedPassword)
             .email(data.getEmail())
             .enabled(userEnabled)
             .passwordToken(passwordToken)
             .passwordTokenExpiration(passwordTokenExpiration)
             .activationToken(activationToken)
             .activationTokenExpiration(activationTokenExpiration)
             .accountNonLocked(true)
             .accountNonExpired(true)
             .credentialsNonExpired(true)
             .build();

        repository.save(newUser);

		if(newUser.getEnabled() == false)
            sendRegistrationEmail(data, activationToken);
    }

    private Boolean enableUser(User data) {
        if(!data.getEnabled()) return false;
    	else return true;
    }

    private UserRole assignUserRole(User data) {
        UserRole role = UserRole.ROLE_USER;

            if (data.getRole().equals("Wolontariusz"))
                role = UserRole.ROLE_USER;

            if (data.getRole().equals("Koordynator"))
                role = UserRole.ROLE_MOD;

            if (data.getRole().equals("Administrator"))
                role = UserRole.ROLE_ADMIN;

        return  role;
    }

    private void sendRegistrationEmail(User data, String activationToken) {
        accountService.sendTokenMail(data.getEmail(), "activationToken", activationToken);
    }

	public List<User> findAllUsers() {
		List<User> userDtoList = new ArrayList<User>();
		List<User> users = repository.findAll();

        users.forEach(user -> {
            User userDto = new User.UserBuilder(user.getUsername(), user.getRole())
                    .id(user.getId())
                    .email(user.getEmail())
                    .failedLogins(user.getFailedLogins())
                    .lastLoginAttempt(user.getLastLoginAttempt())
                    .build();

            userDtoList.add(userDto);
        });

		return userDtoList;
	}

	@Override
	public void updateUser(User data) {
        String password = data.getPassword();
        String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(data));
        User updatedUser = new User.UserBuilder(data.getUsername(), data.getRole())
                .password(encodedPassword)
                .email(data.getEmail())
                .build();

		repository.saveAndFlush(updatedUser);
	}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return repository.findByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

	@Override
	public String setToken() {

		char[] VALID_CHARACTERS =
	    	    "abcdefghijklmnopqrstuvwxyz0123456879".toCharArray();

		SecureRandom srand = new SecureRandom();

	    Random random = new Random();

	    char[] buff = new char[16];

	    for (int i = 0; i < 16; ++i) {

	      if ((i % 10) == 0) {
	          random.setSeed(srand.nextLong());
	      }
	      buff[i] = VALID_CHARACTERS[random.nextInt(VALID_CHARACTERS.length)];
	    }

	    return  String.valueOf(buff);
	}

	@Override
	public Date setTokenExpirationDate() {

		Date currentDate = new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.format(currentDate);

		Calendar cal=Calendar.getInstance();
		cal=format.getCalendar();	
		cal.add(Calendar.MINUTE, 2280);

		return (Date)cal.getTime();
	}

	@Override
	public Boolean checkIfTokenExpired(String tokenType, String token) {

        User userByToken = repository.findByActivationToken(token);

		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if(tokenType == "activationToken")
		format.format(userByToken.getActivationTokenExpiration());
		
		if(tokenType == "passwordToken")
		format.format(userByToken.getPasswordTokenExpiration());

		Calendar expire=Calendar.getInstance();
		expire = format.getCalendar();		

		if(Calendar.getInstance().getTime().after(expire.getTime()) == true) {
			return true;
		} else return false;
	}

	@Override
	public String encodePassword(User data) {
	
		String password = data.getPassword();
    	String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(data));
		
    	return encodedPassword;
	}

	@Override
	public ResponseEntity<String> addUser(User data) {

		HttpStatus responseStatus = HttpStatus.CREATED;

    	JsonObject jsonResponse = new JsonObject();

    	User userFoundByName = repository.findByUsername(data.getUsername().toLowerCase());

    	User userFoundByEmail = repository.findByEmail(data.getEmail().toLowerCase());
    	
			boolean userNameFound = false;
			boolean emailFound =  false;

		    if (userFoundByName != null) {
		    	userNameFound = true;
		    	responseStatus = HttpStatus.CONFLICT;
		    	jsonResponse.addProperty("id", "Resource Conflict");
				jsonResponse.addProperty("description", "duplicateUser");
		    } 	
	        			    		    
		    if (userFoundByEmail != null) {
		    	emailFound = true;
		    	responseStatus = HttpStatus.CONFLICT;
		    	jsonResponse.addProperty("id", "Resource Conflict");
				jsonResponse.addProperty("description", "duplicateEmail");
		    }	
		    
		    if(userNameFound == true && emailFound == true) {
		    	responseStatus = HttpStatus.CONFLICT;
		    	jsonResponse.addProperty("id", "Resource Conflict");
				jsonResponse.addProperty("description", "duplicateUser&Email");
		    }
		    	
		    if(userNameFound == false && emailFound == false) {
		    	createUser(data);
		    	jsonResponse.addProperty("message", "Create Success");
		    }

    	return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}
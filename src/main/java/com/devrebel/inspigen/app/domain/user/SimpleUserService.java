package com.devrebel.inspigen.app.domain.user;

import com.devrebel.inspigen.app.domain.account.AccountService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SimpleUserService implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountService accountService;

    @Override
    public void createUser(User data) {
        String encodedPassword = accountService.encodePassword(data);
        String passwordToken = accountService.generateToken();
        Date passwordTokenExpiration = accountService.setTokenExpirationDate();
        String activationToken = accountService.generateToken();
        Date activationTokenExpiration = accountService.setTokenExpirationDate();
        Boolean userEnabled = enableUser(data);
        UserRole assignedRole = assignUserRole(data);

        User newUser = new User.UserBuilder(data.getUsername(), assignedRole)
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

        userRepository.save(newUser);

        if (newUser.getEnabled() == false)
            sendRegistrationEmail(data, activationToken);
    }

    private Boolean enableUser(User data) {
        if (!data.getEnabled()) return false;
        else return true;
    }

    private UserRole assignUserRole(User data) {
        UserRole role = UserRole.ROLE_USER;

       /* if (data.getRole().equals("Wolontariusz"))
            role = UserRole.ROLE_USER;
        if (data.getRole().equals("Koordynator"))
            role = UserRole.ROLE_MOD;
        if (data.getRole().equals("Administrator"))
            role = UserRole.ROLE_ADMIN;*/

        return role;
    }

    private void sendRegistrationEmail(User data, String activationToken) {
        accountService.sendTokenMail(data.getEmail(), "activationToken", activationToken);
    }

    public List<User> findAllUsers() {
        List<User> userDtoList = new ArrayList<User>();
        List<User> users = userRepository.findAll();
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
        String encodedPassword = accountService.encodePassword(data);
        User updatedUser = new User.UserBuilder(data.getUsername(), data.getRole())
                .password(encodedPassword)
                .email(data.getEmail())
                .build();

        userRepository.saveAndFlush(updatedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepository.findByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> addUser(User data) {
        final User userFoundByName = userRepository.findByUsername(data.getUsername().toLowerCase());
        final User userFoundByEmail = userRepository.findByEmail(data.getEmail().toLowerCase());
        HttpStatus responseStatus = HttpStatus.CREATED;
        JsonObject jsonResponse = new JsonObject();
        boolean userNameFound = false;
        boolean emailFound = false;

        if (null != userFoundByName) {
            userNameFound = true;
            responseStatus = HttpStatus.CONFLICT;
            jsonResponse.addProperty("id", "Resource Conflict");
            jsonResponse.addProperty("description", "duplicateUser");
        }
        if (null != userFoundByEmail) {
            emailFound = true;
            responseStatus = HttpStatus.CONFLICT;
            jsonResponse.addProperty("id", "Resource Conflict");
            jsonResponse.addProperty("description", "duplicateEmail");
        }
        if (userNameFound && emailFound) {
            responseStatus = HttpStatus.CONFLICT;
            jsonResponse.addProperty("id", "Resource Conflict");
            jsonResponse.addProperty("description", "duplicateUser&Email");
        }
        if (!userNameFound && !emailFound) {
            createUser(data);
            jsonResponse.addProperty("message", "Create Success");
        }

        return new ResponseEntity<>(jsonResponse.toString(), responseStatus);
    }
}
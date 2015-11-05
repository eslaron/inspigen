package com.devrebel.inspigen.app.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class SimpleUserService implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAccountService userAccountService;

    @Override
    public void createUser(User data) {
        String encodedPassword = userAccountService.encodePassword(data);
        String passwordToken = userAccountService.generateToken();
        Date passwordTokenExpiration = userAccountService.setTokenExpirationDate();
        String activationToken = userAccountService.generateToken();
        Date activationTokenExpiration = userAccountService.setTokenExpirationDate();
        Boolean userEnabled = enableUser(data);
        UserRole assignedRole = assignUserRole(data);

        User newUser = new User();
        newUser.setPassword(encodedPassword);
        newUser.setEmail(data.getEmail());
        newUser.setEnabled(userEnabled);
        newUser.setPasswordToken(passwordToken);
        newUser.setPasswordTokenExpiration(passwordTokenExpiration);
        newUser.setActivationToken(activationToken);
        newUser.setActivationTokenExpiration(activationTokenExpiration);
        newUser.setAccountNonLocked(true);
        newUser.setAccountNonExpired(true);
        newUser.setCredentialsNonExpired(true);

        userRepository.saveAndFlush(newUser);

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
        userAccountService.sendTokenMail(data.getEmail(), "activationToken", activationToken);
    }

    @Override
    public void updateUser(User data) {
        String encodedPassword = userAccountService.encodePassword(data);
        data.setPassword(encodedPassword);

        userRepository.saveAndFlush(data);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepository.findByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
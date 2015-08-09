package com.devrebel.inspigen.app.domain.account;

import com.devrebel.inspigen.app.domain.user.UserService;
import com.devrebel.inspigen.app.domain.user.User;
import com.devrebel.inspigen.app.domain.user.UserRepository;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    String message = "";

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody User data) {
        return userService.addUser(data);
    }

    @RequestMapping(value = "/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity<String> sendResetPasswordMail(@PathVariable String email) {
        HttpStatus responseStatus = HttpStatus.OK;
        final User userFoundByEmail = userRepository.findByEmail(email);
        if (userFoundByEmail != null) {
            userFoundByEmail.toBuilder().passwordTokenExpiration(accountService.setTokenExpirationDate());
            userService.updateUser(userFoundByEmail);
            accountService.sendTokenMail(email, "passwordToken", userFoundByEmail.getPasswordToken());
            message = "resetLinkSent";
        } else {
            responseStatus = HttpStatus.NOT_FOUND;
            message = "emailNotRegistered";
        }
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", message);

        return new ResponseEntity<>(jsonResponse.toString(), responseStatus);
    }

    @RequestMapping(value = "/accountActivation", method = RequestMethod.PUT)
    public ResponseEntity<String> activateAccount(@RequestBody User user) {
        HttpStatus responseStatus = HttpStatus.NOT_FOUND;
        message = "invalidActivationLink";
        User userFoundByActivationToken = userRepository.findByActivationToken(user.getActivationToken());
        boolean isTokenExpired = accountService.checkIfTokenExpired("activationToken", user.getActivationToken());

        if (userFoundByActivationToken != null) {
            if (isTokenExpired == true) {
                message = "activationLinkExpired";
                responseStatus = HttpStatus.OK;
            } else {
                if (userFoundByActivationToken.getEnabled() == true) {
                    responseStatus = HttpStatus.OK;
                    message = "alreadyActivated";
                } else {
                    userFoundByActivationToken.toBuilder().enabled(true);
                    userService.updateUser(userFoundByActivationToken);
                    responseStatus = HttpStatus.OK;
                    message = "accountActivated";
                }
            }
        }
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", message);

        return new ResponseEntity<>(jsonResponse.toString(), responseStatus);
    }

    @RequestMapping(value = "/passwordReset", method = RequestMethod.PUT)
    public ResponseEntity<String> resetPassword(@RequestBody User user) {
        HttpStatus responseStatus = HttpStatus.NOT_FOUND;
        message = "invalidResetLink";
        User userFoundByPasswordToken = userRepository.findByPasswordToken(user.getPasswordToken());
        final boolean isTokenExpired = accountService.checkIfTokenExpired("passwordToken", user.getPasswordToken());

        if (userFoundByPasswordToken != null) {
            if (isTokenExpired == true) {
                responseStatus = HttpStatus.OK;
                message = "resetLinkExpired";
            } else {
                String newToken = accountService.generateToken();
                String newPassword = accountService.encodePassword(userFoundByPasswordToken);
                userFoundByPasswordToken.toBuilder()
                        .password(newPassword)
                        .passwordToken(newToken);
                userService.updateUser(userFoundByPasswordToken);
                responseStatus = HttpStatus.OK;
                message = "passwordChanged";
            }
        }
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", message);

        return new ResponseEntity<>(jsonResponse.toString(), responseStatus);
    }
}
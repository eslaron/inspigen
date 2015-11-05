package com.devrebel.inspigen.app.domain.user;

import com.devrebel.inspigen.core.web.exception.message.MessageDTO;
import com.devrebel.inspigen.core.web.exception.message.MessageType;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/accounts")
public class UserAccountController extends UserCrudController {

    private static final String EMAIL_NOT_FOUND_ERROR_CODE = "error.user.account.email.not.found";
    private static final String INVALID_RESET_LINK_ERROR_CODE = "error.user.account.reset.link.invalid";
    private static final String RESET_LINK_EXPIRED_ERROR_CODE = "error.user.account.reset.link.expired";
    private static final String PASSWORD_CHANGED_SUCCESS_CODE = "success.user.account.password.changed";
    private static final String RESET_LINK_SENT_SUCCESS_CODE = "success.user.account.reset.link.sent";
    private static final String ACCOUNT_ACTIVATED_SUCCESS_CODE = "success.user.account.activated";
    private static final String INVALID_ACTIVATION_LINK_ERROR_CODE = "error.user.account.invalid.activation.link";
    private static final String ACTIVATION_LINK_EXPIRED_ERROR_CODE = "error.user.account.activation.link.expired";
    private static final String ACCOUNT_ALREADY_ACTIVATED_ERROR_CODE = "info.user.account.already.activated";

    @Autowired
    UserAccountValidator userAccountValidator;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value ="/passwordReset", method = RequestMethod.POST)
    public MessageDTO sendResetPasswordMail(@RequestBody String email, HttpServletResponse response) {
        MessageDTO message = new MessageDTO(MessageType.SUCCESS, RESET_LINK_SENT_SUCCESS_CODE);
        final User userFoundByEmail = repository.findByEmail(email);

        if(userFoundByEmail == null) {
            final Locale currentLocale = LocaleContextHolder.getLocale();
            final String emailNotFoundMessage = messageSource.getMessage(EMAIL_NOT_FOUND_ERROR_CODE , null, currentLocale);
            message = new MessageDTO(MessageType.ERROR, emailNotFoundMessage);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            userFoundByEmail.setPasswordTokenExpiration(userAccountService.setTokenExpirationDate());
            repository.saveAndFlush(userFoundByEmail);
            userAccountService.sendTokenMail(email, "passwordToken", userFoundByEmail.getPasswordToken());
        }

        return message;
    }

    @RequestMapping(value = "/passwordReset", method = RequestMethod.PUT)
    public MessageDTO resetPassword(@RequestBody User user, HttpServletResponse response) {
        MessageDTO message = new MessageDTO(MessageType.SUCCESS, PASSWORD_CHANGED_SUCCESS_CODE);
        User userFoundByPasswordToken = repository.findByPasswordToken(user.getPasswordToken());
        boolean isTokenExpired = false;

        if(userFoundByPasswordToken == null) {
            message = new MessageDTO(MessageType.ERROR, INVALID_RESET_LINK_ERROR_CODE);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            isTokenExpired = userAccountService.checkIfTokenExpired("passwordToken", user.getPasswordToken());
        }

        if(isTokenExpired) {
            message = new MessageDTO(MessageType.ERROR, RESET_LINK_EXPIRED_ERROR_CODE);
        } else {
            String newToken = userAccountService.generateToken();
            String newPassword = userAccountService.encodePassword(userFoundByPasswordToken);
            userFoundByPasswordToken.setPassword(newPassword);
            userFoundByPasswordToken.setPasswordToken(newToken);
            userService.updateUser(userFoundByPasswordToken);
        }

        return message;
    }

    @RequestMapping(value = "/activateAccount", method = RequestMethod.PUT)
    public MessageDTO activateAccount(@RequestBody User user, HttpServletResponse response) {
        MessageDTO message = new MessageDTO(MessageType.SUCCESS, ACCOUNT_ACTIVATED_SUCCESS_CODE);
        User userFoundByActivationToken = repository.findByActivationToken(user.getActivationToken());
        boolean isTokenExpired = false;

        if(userFoundByActivationToken == null) {
            message = new MessageDTO(MessageType.ERROR, INVALID_ACTIVATION_LINK_ERROR_CODE);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            isTokenExpired = userAccountService.checkIfTokenExpired("activationToken", user.getActivationToken());
        }

        if(isTokenExpired) {
            message = new MessageDTO(MessageType.ERROR, ACTIVATION_LINK_EXPIRED_ERROR_CODE);
        } else if(userFoundByActivationToken.getEnabled()){
            message = new MessageDTO(MessageType.INFO,  ACCOUNT_ALREADY_ACTIVATED_ERROR_CODE);
        } else {
            userFoundByActivationToken.setEnabled(true);
            userService.updateUser(userFoundByActivationToken);
        }

        return message;
    }
}
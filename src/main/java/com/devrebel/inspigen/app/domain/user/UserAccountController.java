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
    private static final String RESET_LINK_SENT_SUCCESS_CODE = " success.user.account.reset.link.sent";

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

    /*@RequestMapping(value = "/accountActivation", method = RequestMethod.PUT)
    public ResponseEntity<String> activateAccount(@RequestBody User user) {
        HttpStatus responseStatus = HttpStatus.NOT_FOUND;
        message = "invalidActivationLink";
        User userFoundByActivationToken = userRepository.findByActivationToken(user.getActivationToken());
        boolean isTokenExpired = userAccountService.checkIfTokenExpired("activationToken", user.getActivationToken());

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
    }*/
}
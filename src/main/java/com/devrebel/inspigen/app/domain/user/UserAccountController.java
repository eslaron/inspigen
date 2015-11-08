package com.devrebel.inspigen.app.domain.user;

import com.devrebel.inspigen.core.web.exception.message.MessageDTO;
import com.devrebel.inspigen.core.web.exception.message.MessageType;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/accounts")
public class UserAccountController extends UserCrudController {

    private static final String EMAIL_NOT_FOUND_ERROR_CODE = "error.user.account.email.not.found";
    private static final String PASSWORD_CHANGED_SUCCESS_CODE = "success.user.account.password.changed";

    private static final String RESET_LINK_INVALID_ERROR_CODE = "error.user.account.reset.link.invalid";
    private static final String RESET_LINK_EXPIRED_INFO_CODE = "info.user.account.reset.link.expired";
    private static final String RESET_LINK_SENT_SUCCESS_CODE = "success.user.account.reset.link.sent";

    private static final String ACCOUNT_ACTIVATED_SUCCESS_CODE = "success.user.account.activated";
    private static final String ACCOUNT_ALREADY_ACTIVATED_INFO_CODE = "info.user.account.already.activated";

    private static final String ACTIVATION_LINK_INVALID_ERROR_CODE = "error.user.account.invalid.activation.link";
    private static final String ACTIVATION_LINK_EXPIRED_INFO_CODE = "info.user.account.activation.link.expired";

    MessageDTO message;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private Mapper dtoMapper;

    @RequestMapping(value ="/resetPassword", method = RequestMethod.POST)
    public MessageDTO sendResetPasswordMail(@RequestBody String email, HttpServletResponse response) {
        final User userFoundByEmail = repository.findByEmail(email);
        final boolean emailNotFound = userFoundByEmail == null;

        if(emailNotFound) {
            rejectIfEmailNotFound(response);
        } else {
            sendPasswordResetEmail(userFoundByEmail);
        }

        return message;
    }

    private void rejectIfEmailNotFound(HttpServletResponse response) {
        final Locale currentLocale = LocaleContextHolder.getLocale();
        final String emailNotFoundMessage =
                messageSource.getMessage(EMAIL_NOT_FOUND_ERROR_CODE, null, currentLocale);
        message = new MessageDTO(MessageType.ERROR, emailNotFoundMessage);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    private void sendPasswordResetEmail(User userFoundByEmail) {
        final Locale currentLocale = LocaleContextHolder.getLocale();
        final String newToken = userAccountService.generateToken();
        final Date tokenExpirationDate = userAccountService.setTokenExpirationDate();
        final String email =  userFoundByEmail.getEmail();

        userFoundByEmail.setPasswordToken(newToken);
        userFoundByEmail.setPasswordTokenExpiration(tokenExpirationDate);
        repository.saveAndFlush(userFoundByEmail);

        userAccountService.sendTokenMail(email, "passwordToken", newToken);

        String resetLinkSentMessage =
                messageSource.getMessage(RESET_LINK_SENT_SUCCESS_CODE, null, currentLocale);
        message = new MessageDTO(MessageType.SUCCESS, resetLinkSentMessage);
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.PUT)
    public MessageDTO resetPassword(@RequestBody User user, HttpServletResponse response) {
        User userFoundByPasswordToken = repository.findByPasswordToken(user.getPasswordToken());
        boolean tokenNotFound = userFoundByPasswordToken == null;

        if(tokenNotFound) {
            rejectIfPasswordTokenNotFound(response);
        } else {
            String newPassword = user.getPassword();
            resetPassword(newPassword, userFoundByPasswordToken);
        }

        return message;
    }

    private void rejectIfPasswordTokenNotFound(HttpServletResponse response) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String invalidPasswordTokenMessage =
                messageSource.getMessage(RESET_LINK_INVALID_ERROR_CODE, null, currentLocale);
        message = new MessageDTO(MessageType.ERROR, invalidPasswordTokenMessage);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    private void resetPassword(String newPassword, User userFoundByPasswordToken) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        boolean isTokenExpired = userAccountService.checkIfTokenExpired("passwordToken",
                userFoundByPasswordToken.getPasswordToken());

        if(isTokenExpired) {
            String passwordTokenExpiredMessage =
                    messageSource.getMessage(RESET_LINK_EXPIRED_INFO_CODE, null, currentLocale);
            message = new MessageDTO(MessageType.INFO, passwordTokenExpiredMessage);
        } else {
            String newToken = userAccountService.generateToken();
            userFoundByPasswordToken.setPassword(newPassword);
            String encodedPassword = userAccountService.encodePassword(userFoundByPasswordToken);
            userFoundByPasswordToken.setPassword(encodedPassword);
            userFoundByPasswordToken.setPasswordToken(newToken);
            repository.saveAndFlush(userFoundByPasswordToken);
            String passwordChangedMessage =
                    messageSource.getMessage(PASSWORD_CHANGED_SUCCESS_CODE, null, currentLocale);
            message = new MessageDTO(MessageType.SUCCESS, passwordChangedMessage);
        }

    }

    @RequestMapping(value = "/activateAccount", method = RequestMethod.PUT)
    public MessageDTO activateAccount(@RequestBody User user, HttpServletResponse response) {
        User userFoundByActivationToken = repository.findByActivationToken(user.getActivationToken());
        boolean tokenNotFound = userFoundByActivationToken == null;
        boolean alreadyActivated = tokenNotFound ? false : userFoundByActivationToken.getEnabled();

        if(tokenNotFound) {
            rejectIfActivationTokenNotFound(response);
        }  else {
            activateAccount(userFoundByActivationToken, alreadyActivated);
        }

        return message;
    }

    private void rejectIfActivationTokenNotFound(HttpServletResponse response) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String invalidActivationTokenMessage =
                messageSource.getMessage(ACTIVATION_LINK_INVALID_ERROR_CODE, null, currentLocale);
        message = new MessageDTO(MessageType.ERROR, invalidActivationTokenMessage);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    private void activateAccount(User userFoundByActivationToken, boolean alreadyActivated) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        boolean isTokenExpired = userAccountService.checkIfTokenExpired("activationToken",
                userFoundByActivationToken.getActivationToken());

        if(isTokenExpired) {
            String activationTokenExpiredMessage =
                    messageSource.getMessage(ACTIVATION_LINK_EXPIRED_INFO_CODE, null, currentLocale);
            message = new MessageDTO(MessageType.INFO, activationTokenExpiredMessage);
        } else if(alreadyActivated) {
            String accountAlreadyActivatedMessage =
                    messageSource.getMessage(ACCOUNT_ALREADY_ACTIVATED_INFO_CODE, null, currentLocale);
            message = new MessageDTO(MessageType.INFO, accountAlreadyActivatedMessage);
        } else {
            userFoundByActivationToken.setEnabled(true);
            repository.saveAndFlush(userFoundByActivationToken);
            String accountActivatedMessage =
                    messageSource.getMessage(ACCOUNT_ACTIVATED_SUCCESS_CODE, null, currentLocale);
            message = new MessageDTO(MessageType.SUCCESS, accountActivatedMessage);
        }
    }
}
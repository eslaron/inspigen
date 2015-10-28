package com.devrebel.inspigen.app.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;

@Component
public class UserValidator implements Validator {

    private static final String USERNAME_DUPLICATE_ERROR_CODE = "error.username.duplicate";
    private static final String EMAIL_DUPLICATE_ERROR_CODE = "error.email.duplicate";

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageSource messageSource;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final User user = (User) target;
        final String usernameToValidate = user.getUsername().toLowerCase();
        final String emailToValidate = user.getEmail().toLowerCase();
        final User userByUsername = userRepository.findByUsername(usernameToValidate);
        final User userInDatabase = userByUsername != null ? userByUsername : userRepository.findByEmail(emailToValidate);

        if(userInDatabase != null) {
            final String usernameInDatabase = userInDatabase.getUsername();
            final String emailInDatabase = userInDatabase.getEmail();

            rejectUsernameDuplicate(errors, usernameToValidate, usernameInDatabase);
            rejectEmailDuplicate(errors, emailToValidate, emailInDatabase);
        }
    }

    private void rejectUsernameDuplicate(Errors errors, String usernameToValidate, String usernameInDatabase) {
        final Locale currentLocale = LocaleContextHolder.getLocale();

        if (usernameToValidate.equals(usernameInDatabase)) {
            String duplicateUsernameMessage = messageSource.getMessage(USERNAME_DUPLICATE_ERROR_CODE, null, currentLocale);
            errors.rejectValue("username", duplicateUsernameMessage);
        }
    }

    private void rejectEmailDuplicate(Errors errors, String emailToValidate, String emailInDatabase) {
        final Locale currentLocale = LocaleContextHolder.getLocale();

        if (emailToValidate.equals(emailInDatabase)) {
            String duplicateEmailMessage = messageSource.getMessage(EMAIL_DUPLICATE_ERROR_CODE, null, currentLocale);
            errors.rejectValue("email", duplicateEmailMessage);
        }
    }
}
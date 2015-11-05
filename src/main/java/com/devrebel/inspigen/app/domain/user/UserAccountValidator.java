package com.devrebel.inspigen.app.domain.user;

import com.devrebel.inspigen.app.domain.user.User;
import com.devrebel.inspigen.app.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;

@Component
public class UserAccountValidator implements Validator {

    private static final String EMAIL_NOT_FOUND_ERROR_CODE = "error.user.account.email.not.found";

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    UserService userService;

    @Autowired
    UserAccountService userAccountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final User userFoundByEmail = (User) target;
        if (userFoundByEmail == null) {
            rejectEmailNotFound(errors);
        }
    }

    private void rejectEmailNotFound(Errors errors) {
        final Locale currentLocale = LocaleContextHolder.getLocale();
        String emailNotFoundMessage = messageSource.getMessage(EMAIL_NOT_FOUND_ERROR_CODE, null, currentLocale);
        errors.rejectValue("email", emailNotFoundMessage);
    }
}

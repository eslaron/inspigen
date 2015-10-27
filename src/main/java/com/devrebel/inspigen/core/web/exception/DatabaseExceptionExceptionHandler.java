package com.devrebel.inspigen.core.web.exception;

import com.devrebel.inspigen.core.web.exception.message.MessageDTO;
import com.devrebel.inspigen.core.web.exception.message.MessageType;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@ControllerAdvice(annotations = RestController.class)
public class DatabaseExceptionExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public MessageDTO handleConflict(DataIntegrityViolationException ex) {
        String constraintName = ((ConstraintViolationException) ex.getCause()).getConstraintName();
        String sqlMessage = ((ConstraintViolationException)ex.getCause()).getSQLException().getMessage();
        String sqlErrorType = sqlMessage.split("\\'")[0];

        return processSQLError(constraintName,sqlErrorType);
    }

    private MessageDTO processSQLError(String constraintName, String sqlErrorType) {
        MessageDTO message = null;
        if (constraintName!= null && sqlErrorType!=null) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            String errorMessage = sqlErrorType + "for field " + constraintName;


            //String msg = msgSource.getMessage(errorMessage, null, currentLocale);
            message = new MessageDTO(MessageType.ERROR, errorMessage);
        }
        return message;
    }
}
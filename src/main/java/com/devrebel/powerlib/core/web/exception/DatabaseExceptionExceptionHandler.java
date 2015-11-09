package com.devrebel.powerlib.core.web.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class DatabaseExceptionExceptionHandler {

    /*@ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public MessageDTO handleConflict(DataIntegrityViolationException ex) {
        String constraintName = ((ConstraintViolationException) ex.getCause()).getConstraintName();
        String sqlMessage = ((ConstraintViolationException)ex.getCause()).getSQLException().getMessage();
        String sqlErrorType = sqlMessage.split("\\ '")[0];

        return processSQLError(constraintName,sqlErrorType);
    }

    private MessageDTO processSQLError(String constraintName, String sqlErrorType) {
        MessageDTO message = null;
        if (constraintName!= null && sqlErrorType!=null) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            String errorMessage = sqlErrorType + " for field " + constraintName;


            //String msg = msgSource.getMessage(errorMessage, null, currentLocale);
            message = new MessageDTO(MessageType.ERROR, errorMessage);
        }
        return message;
    }*/
}
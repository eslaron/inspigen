package com.devrebel.powerlib.core.web.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class FormValidationExceptionHandler {

    @Autowired
    private MessageSource msgSource;

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<MessageDTO> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> errors = result.getFieldErrors();
        return processFieldErrors(errors);
    }

    private List<MessageDTO> processFieldErrors(List<FieldError> errors) {
        List<MessageDTO> errorMessages = new ArrayList<>();
        if (!errors.isEmpty()) {
            errors.forEach(error ->{
                Locale currentLocale = LocaleContextHolder.getLocale();
                String msg = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
                MessageDTO errorMessage = new MessageDTO(MessageType.ERROR, msg);
                errorMessages.add(errorMessage);
            });
        }
        return errorMessages;
    }*/
}
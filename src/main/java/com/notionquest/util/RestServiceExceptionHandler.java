package com.notionquest.util;

import com.notionquest.model.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class RestServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger LOG = Logger.getLogger(getClass().getName());

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleException (RuntimeException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrors(Arrays.asList(ex.getMessage()));
        LOG.log(Level.WARNING, "Illegal argument exception :" + exceptionResponse);
        return handleExceptionInternal(ex, exceptionResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}

package com.MyProject.mediationplatform.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<Object> handleException(
            RuntimeException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());
        body.put("description", ex.getMessage());

        HttpStatus status;
        if(ex instanceof ApplicationNotFoundException) {
            body.put("code", String.valueOf(HttpStatus.FORBIDDEN.value()));
            status = HttpStatus.FORBIDDEN;
        } else if (ex instanceof CustomerLinksException) {
            body.put("code", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            body.put("code", String.valueOf(HttpStatus.NOT_FOUND.value()));
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(body, status);
    }

}

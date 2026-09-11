package com.MyProject.mediationplatform.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomerLinksException extends RuntimeException{

    private static final long serialVersionUID = 6089026452264509602L;

    public CustomerLinksException(String message) {
        super(message);
    }
}

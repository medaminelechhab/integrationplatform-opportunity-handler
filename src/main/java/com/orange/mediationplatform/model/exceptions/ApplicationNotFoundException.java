package com.MyProject.mediationplatform.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ApplicationNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 569508602264718229L;

    public ApplicationNotFoundException(String application) {
        super("Unknown application: " + application);
    }
}

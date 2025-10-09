package com.reloaded.sales.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class UserExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserExistsException(String message) {
        super(message);
    }
}

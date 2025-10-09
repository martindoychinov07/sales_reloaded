package com.reloaded.sales.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectPasswordException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
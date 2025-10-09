package com.example.sales.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class PartnerAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PartnerAlreadyExistsException(String message) {
        super(message);
    }
}

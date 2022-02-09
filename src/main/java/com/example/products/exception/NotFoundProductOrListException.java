package com.example.products.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundProductOrListException extends Exception{
    public NotFoundProductOrListException(String message) {
        super(message);
    }
}

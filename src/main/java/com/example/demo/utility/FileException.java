package com.example.demo.utility;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileException extends RuntimeException {
    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
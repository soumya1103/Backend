package com.libraryManagement.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class ResourceNotAllowedToDeleteException extends RuntimeException {
    public ResourceNotAllowedToDeleteException(String message) {
        super(message);
    }
}

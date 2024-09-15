package com.libraryManagement.backend.exception;

import com.libraryManagement.backend.dto.response.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });

        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalException(Exception exception,
                                                             WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                exception.getMessage()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException exception,
                                                                            WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.CONFLICT.toString(),
                exception.getMessage()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                            WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.NOT_FOUND.toString(),
                exception.getMessage()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentialsException(BadCredentialsException exception,
                                                                       WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.UNAUTHORIZED.toString(),
                exception.getMessage()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUsernameNotFoundException(UsernameNotFoundException exception,
                                                                     WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.NOT_FOUND.toString(),
                exception.getMessage()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotAllowedToDeleteException.class)
    public ResponseEntity<ApiResponse> handleResourceNotAllowedToDeleteException(MethodNotAllowedException exception,
                                                                       WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.METHOD_NOT_ALLOWED.toString(),
                exception.getMessage()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolationException(DataIntegrityViolationException exception,
                                                                             WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.BAD_REQUEST.toString(),
                "Operation failed."
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}

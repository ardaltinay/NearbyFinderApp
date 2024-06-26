package com.nearbyfinder.NearbyFinderApp.exception;

import com.nearbyfinder.NearbyFinderApp.model.response.GenericErrorResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        for (ObjectError objectError : ex.getBindingResult().getGlobalErrors()) {
            errors.add(objectError.getObjectName() + ": " + objectError.getDefaultMessage());
        }
        return buildErrorResponseEntity(new GenericErrorResponse(HttpStatus.BAD_REQUEST, errors, LocalDateTime.now()));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatusCode status, WebRequest request) {
        String error = ex.getPropertyName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName();
        return buildErrorResponseEntity(new GenericErrorResponse(HttpStatus.BAD_REQUEST, error, LocalDateTime.now()));
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    protected ResponseEntity<Object> handlePlaceNotFoundException(PlaceNotFoundException e) {
        GenericErrorResponse errorResponse = new GenericErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), LocalDateTime.now());
        return buildErrorResponseEntity(errorResponse);
    }

    private ResponseEntity<Object> buildErrorResponseEntity(GenericErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

}

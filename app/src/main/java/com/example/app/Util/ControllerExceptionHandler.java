package com.example.app.Util;

import org.apache.coyote.Response;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {


    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpClientErrorException.UnprocessableEntity.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error.");

        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }


    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {

            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "User with that username already exists!");

        return ResponseEntity.badRequest().body(errorResponse);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleUserNotFoundException(UsernameNotFoundException ex, WebRequest request){

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),"User with that username doesn't exist!");

        return new ResponseEntity<Object>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
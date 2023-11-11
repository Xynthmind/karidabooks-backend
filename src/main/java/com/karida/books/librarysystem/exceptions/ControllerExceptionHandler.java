package com.karida.books.librarysystem.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<Object> handleInvalidJson(JsonProcessingException ex) {

        return new ResponseEntity<>("Invalid JSON", HttpStatus.NOT_FOUND);
    }
}
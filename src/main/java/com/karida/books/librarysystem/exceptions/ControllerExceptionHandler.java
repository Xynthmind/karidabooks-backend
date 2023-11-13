package com.karida.books.librarysystem.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<Object> handleInvalidJson() {
        return new ResponseEntity<>("Invalid JSON", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleInvalidMethod(){
        return new ResponseEntity<>("Invalid request method", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(SQLGrammarException.class)
    public ResponseEntity<Object> handleInvalidSQL(SQLGrammarException  ex){
        return new ResponseEntity<>("Invalid request sql"+ ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleInvalidSQL(){
        return new ResponseEntity<>("JSON body is empty", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
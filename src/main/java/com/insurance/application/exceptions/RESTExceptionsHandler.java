package com.insurance.application.exceptions;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import java.time.LocalDateTime;

@RestControllerAdvice("com.insurance.application.controllers.rest.")
public class RESTExceptionsHandler {


    private static final Logger logger = LoggerFactory.getLogger(com.insurance.application.exceptions.MvcExceptionHandler.class);

    @ExceptionHandler(value = {HibernateException.class})
    public ResponseEntity<Object> handleHibenateException(HibernateException ex) {
        logger.error("Hibernate Malfunction: ", ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity.status( HttpStatus.BAD_REQUEST).body(new ErrorInformation("Database Error", LocalDateTime.now()));

    }

    @ExceptionHandler(value = {RollbackException.class})
    public ResponseEntity<Object> handleRollbackException(RollbackException ex) {
        logger.error("Hibernate Malfunction: ", ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<Object>("Update failed, please try again later", HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value = {NoResultException.class})
    public ResponseEntity<Object> handleInvalidInputException(NoResultException ex) {
        logger.error("No info for query found: ", ex.getMessage());
        return new ResponseEntity<Object>("No data found for your request", HttpStatus.NO_CONTENT);

    }

    @ExceptionHandler(value = {NonUniqueResultException.class})
    public ResponseEntity<Object> handleInvalidInputException(NonUniqueResultException ex) {
        logger.error("More than one results found for query: ", ex.getMessage());
        return new ResponseEntity<Object>("More than one item found for your request", HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleInvalidInputException(EntityNotFoundException ex) {
        logger.error("No Info found for query: ", ex.getMessage());
        return ResponseEntity.status( HttpStatus.NOT_FOUND).body(new ErrorInformation(ex.getMessage(), LocalDateTime.now()));
//        return new ResponseEntity<Object>("No info availble", HttpStatus.NOT_FOUND);
    }
}



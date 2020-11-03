package com.insurance.application.exceptions;


import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;

@ControllerAdvice
public class MvcExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(MvcExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {HibernateException.class})
    public ResponseEntity<Object> handleHibenateException(HibernateException ex) {
        logger.error("Hibernate Malfunction: ", ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<Object>("Somethnig went wrong, please try again later", HttpStatus.BAD_REQUEST);

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {RollbackException.class})
    public ResponseEntity<Object> handleRollbackException(RollbackException ex) {
        logger.error("Hibernate Malfunction: ", ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<Object>("Update failed, please try again later", HttpStatus.BAD_REQUEST);

    }


    @org.springframework.web.bind.annotation.ExceptionHandler(value = {NoResultException.class})
    public ResponseEntity<Object> handleInvalidInputException(NoResultException ex) {
        logger.error("No info for query found: ", ex.getMessage());
        return new ResponseEntity<Object>("No data found for your request", HttpStatus.NO_CONTENT);

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {NonUniqueResultException.class})
    public ResponseEntity<Object> handleInvalidInputException(NonUniqueResultException ex) {
        logger.error("More than one results found for query: ", ex.getMessage());
        return new ResponseEntity<Object>("More than one item found for your request", HttpStatus.BAD_REQUEST);

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleInvalidInputException(EntityNotFoundException ex) {
        logger.error("No Info found for query: ", ex.getMessage());
        return new ResponseEntity<Object>("No info availble", HttpStatus.NOT_FOUND);
    }
}

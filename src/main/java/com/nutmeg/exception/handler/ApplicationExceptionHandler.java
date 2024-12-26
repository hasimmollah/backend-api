
package com.nutmeg.exception.handler;

import com.nutmeg.exception.*;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.testcontainers.shaded.org.apache.commons.lang3.StringUtils;


/**
 * Advice to handle exception
 * 
 * @author hasmolla
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackages = { "com.nutmeg.controller" })
public class ApplicationExceptionHandler {

    private static final Logger logger = LogManager.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleApplicationNotFoundException(
        ApplicationNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = ex.getMessage() ==null ? ErrorCodes.APPLICATION_NOT_FOUND.getErrorResponse() : new ErrorResponse(ErrorCodes.APPLICATION_NOT_FOUND.getCode(), ex.getMessage());
        logger.error(error, ex);

        return new ResponseEntity<>(
            error,
            HttpStatus.NOT_FOUND);
    }
    
    
    
    @ExceptionHandler(ApplicationDownstreamException.class)
    public ResponseEntity<ErrorResponse> handleApplicationDownstreamException(
    		ApplicationDownstreamException ex, HttpServletRequest request) {
        ErrorResponse error = StringUtils.isBlank(ex.getMessage()) ? ErrorCodes.DOWNSTREAM_EXCEPTION.getErrorResponse() : new ErrorResponse(ErrorCodes.APPLICATION_NOT_FOUND.getCode(), ex.getMessage());

        logger.error(error, ex);

        return new ResponseEntity<>(
            error,
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
    

    
    @ExceptionHandler(ApplicationSQLException.class)
    public ResponseEntity<ErrorResponse> handleApplicationSQLException(HttpServletRequest request,
        ApplicationSQLException ex) {
        ErrorResponse error = ErrorCodes.SQL_EXCEPTION.getErrorResponse();
        logger.error(error, ex);
        return new ResponseEntity<>(
            error,
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request,
        Exception ex) {
        ErrorResponse error = ErrorCodes.APPLICATION_EXCEPTION.getErrorResponse();
        logger.error(error, ex);
        return new ResponseEntity<>(
            error,
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(HttpServletRequest request,
                                                         MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse error = msg != null? new ErrorResponse(ErrorCodes.MANDATORY_FIELD.getCode(), msg) :ErrorCodes.MANDATORY_FIELD.getErrorResponse();
        logger.error(error, ex);
        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> handleResourceAccessException(HttpServletRequest request,
                                                                       ResourceAccessException ex) {
        ErrorResponse error = ErrorCodes.DOWNSTREAM_EXCEPTION.getErrorResponse();

        logger.error(error, ex);
        return new ResponseEntity<>(
                error,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
        HttpServletRequest request, HttpMessageNotReadableException ex) {
        ErrorResponse error = ErrorCodes.MANDATORY_FIELD.getErrorResponse();
        logger.error(error, ex);
        return new ResponseEntity<>(
            error,
            HttpStatus.BAD_REQUEST);
    }

}

package com.example.backend.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The username or password is incorrect: " + ex.getMessage());
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<String> handleAccountStatusException(AccountStatusException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The account is locked: " + ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to access this resource: " + ex.getMessage());
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> handleSignatureException(SignatureException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The JWT signature is invalid: " + ex.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The JWT token has expired: " + ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown internal server error: " + ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + ex.getMessage());
    }
}

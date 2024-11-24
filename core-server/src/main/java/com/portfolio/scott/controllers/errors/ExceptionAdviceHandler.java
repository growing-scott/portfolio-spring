package com.portfolio.scott.controllers.errors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdviceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("code", "INTERNAL_SERVER_ERROR");
        errorDetails.put("message", ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorDetails.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAllowedUserException.class)
    public ResponseEntity<Map<String, Object>> handleNotAllowedException(NotAllowedUserException ex) {
        return new ResponseEntity<>(handleErrorResponse(ex), ex.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(handleErrorResponse(ex), ex.getStatus());
    }

    @ExceptionHandler(AlreadyUserException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyUserException(AlreadyUserException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex), ex.getStatus());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>(handleErrorResponse(ex), ex.getStatus());
    }

    @ExceptionHandler(AlreadyScrapException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyScrapException(AlreadyScrapException ex) {
        return new ResponseEntity<>(handleErrorResponse(ex), ex.getStatus());
    }

    @ExceptionHandler(NotFoundTaxBaseException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundTaxBaseException(NotFoundTaxBaseException ex) {
        return new ResponseEntity<>(handleErrorResponse(ex), ex.getStatus());
    }

    @ExceptionHandler(UserPasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handleUserPasswordMismatchException(UserPasswordMismatchException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex), ex.getStatus());
    }

    @ExceptionHandler(NotFoundUserIncomeException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundUserIncomeException(NotFoundUserIncomeException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex), ex.getStatus());
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ErrorResponse> handleLoginFailedException(LoginFailedException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex), ex.getStatus());
    }

    @ExceptionHandler(EncryptionDecryptFailedException.class)
    public ResponseEntity<ErrorResponse> handleEncryptionDecryptFailedException(EncryptionDecryptFailedException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex), ex.getStatus());
    }

    @ExceptionHandler(EncryptionEncryptFailedException.class)
    public ResponseEntity<ErrorResponse> handleEncryptionEncryptFailedException(EncryptionEncryptFailedException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex), ex.getStatus());
    }

    private Map<String, Object> handleErrorResponse(ApiException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", ex.getStatus().value());
        error.put("errorCode", ex.getErrorCode());
        error.put("errorMessage", ex.getErrorMessage());
        return error;
    }
}

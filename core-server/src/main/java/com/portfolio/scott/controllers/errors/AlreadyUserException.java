package com.portfolio.scott.controllers.errors;

import org.springframework.http.HttpStatus;

public class AlreadyUserException extends ApiException {

    public AlreadyUserException() {
        super(HttpStatus.CONFLICT, ErrorCode.USER_ALREADY_SIGNUP, ErrorCode.USER_ALREADY_SIGNUP.getMessage());
    }

    public AlreadyUserException(String message) {
        super(HttpStatus.CONFLICT, ErrorCode.USER_ALREADY_SIGNUP, message);
    }
}

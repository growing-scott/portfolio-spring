package com.portfolio.scott.controllers.errors;

import org.springframework.http.HttpStatus;

public class NotAllowedUserException extends ApiException {

    public NotAllowedUserException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.USER_NOT_ALLOWED_SIGNUP, ErrorCode.USER_NOT_ALLOWED_SIGNUP.getMessage());
    }

    public NotAllowedUserException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.USER_NOT_ALLOWED_SIGNUP, message);
    }
}

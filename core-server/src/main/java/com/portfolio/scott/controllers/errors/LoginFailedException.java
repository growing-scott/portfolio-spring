package com.portfolio.scott.controllers.errors;

import org.springframework.http.HttpStatus;

public class LoginFailedException extends ApiException {

    public LoginFailedException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.LOGIN_FAILURE, ErrorCode.LOGIN_FAILURE.getMessage());
    }

    public LoginFailedException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.LOGIN_FAILURE, message);
    }
}

package com.portfolio.scott.controllers.errors;

import org.springframework.http.HttpStatus;

public class UserPasswordMismatchException extends ApiException {

    public UserPasswordMismatchException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.USER_PASSWORD_MISMATCHED, ErrorCode.USER_PASSWORD_MISMATCHED.getMessage());
    }

    public UserPasswordMismatchException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.USER_PASSWORD_MISMATCHED, message);
    }
}

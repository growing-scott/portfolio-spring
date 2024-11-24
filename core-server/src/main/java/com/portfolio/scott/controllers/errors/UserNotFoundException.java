package com.portfolio.scott.controllers.errors;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage());
    }

    public UserNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND, message);
    }
}

package com.portfolio.scott.controllers.errors;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED_ERROR, ErrorCode.UNAUTHORIZED_ERROR.getMessage());
    }

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED_ERROR, message);
    }
}

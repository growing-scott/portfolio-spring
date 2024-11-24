package com.portfolio.scott.controllers.errors;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -737318266745235166L;

    private final HttpStatus status;

    private final ErrorCode errorCode;

    private final String errorMessage;

    public ApiException(HttpStatus status, ErrorCode errorCode) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }

    public ApiException(HttpStatus status, ErrorCode errorCode, String errorMessage) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}

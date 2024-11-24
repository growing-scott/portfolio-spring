package com.portfolio.scott.controllers.errors;

import org.springframework.http.HttpStatus;

public class AlreadyScrapException extends ApiException {

    public AlreadyScrapException() {
        super(HttpStatus.CONFLICT, ErrorCode.SCRAP_REQUEST_ALREADY, ErrorCode.SCRAP_REQUEST_ALREADY.getMessage());
    }

    public AlreadyScrapException(String message) {
        super(HttpStatus.CONFLICT, ErrorCode.SCRAP_REQUEST_ALREADY, message);
    }
}

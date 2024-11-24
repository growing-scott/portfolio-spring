package com.portfolio.scott.controllers.errors;

import org.springframework.http.HttpStatus;

public class NotFoundTaxBaseException extends ApiException {

    public NotFoundTaxBaseException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_TAX_BASE, ErrorCode.NOT_FOUND_TAX_BASE.getMessage());
    }

    public NotFoundTaxBaseException(String message) {
        super(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_TAX_BASE, message);
    }
}

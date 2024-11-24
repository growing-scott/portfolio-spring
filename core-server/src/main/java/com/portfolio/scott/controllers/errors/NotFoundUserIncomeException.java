package com.portfolio.scott.controllers.errors;

import org.springframework.http.HttpStatus;

public class NotFoundUserIncomeException extends ApiException {

    public NotFoundUserIncomeException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_USER_INCOME, ErrorCode.NOT_FOUND_USER_INCOME.getMessage());
    }

    public NotFoundUserIncomeException(String message) {
        super(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_USER_INCOME, message);
    }
}

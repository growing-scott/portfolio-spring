package com.portfolio.scott.controllers.errors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private ErrorCode errorCode;
    private int status;
    private String errorMessage;

    public ErrorResponse(ApiException ex) {
        errorCode = ex.getErrorCode();
        status = ex.getStatus().value();
        errorMessage = ex.getErrorMessage();
    }
}

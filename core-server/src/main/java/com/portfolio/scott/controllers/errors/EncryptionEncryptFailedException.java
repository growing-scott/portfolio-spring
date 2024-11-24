package com.portfolio.scott.controllers.errors;

import org.springframework.http.HttpStatus;

public class EncryptionEncryptFailedException extends ApiException {

    public EncryptionEncryptFailedException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.ENCRYPTION_ENCRYPT_FAILED, ErrorCode.ENCRYPTION_ENCRYPT_FAILED.getMessage());
    }

    public EncryptionEncryptFailedException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.ENCRYPTION_ENCRYPT_FAILED, message);
    }
}

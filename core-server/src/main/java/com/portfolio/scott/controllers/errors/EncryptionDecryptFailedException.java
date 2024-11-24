package com.portfolio.scott.controllers.errors;

import org.springframework.http.HttpStatus;

public class EncryptionDecryptFailedException extends ApiException {

    public EncryptionDecryptFailedException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.ENCRYPTION_DECRYPT_FAILED, ErrorCode.ENCRYPTION_DECRYPT_FAILED.getMessage());
    }

    public EncryptionDecryptFailedException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.ENCRYPTION_DECRYPT_FAILED, message);
    }
}

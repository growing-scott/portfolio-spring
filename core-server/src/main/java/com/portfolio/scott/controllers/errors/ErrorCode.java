package com.portfolio.scott.controllers.errors;

public enum ErrorCode {
    USER_ALREADY_SIGNUP("The user has already signed up."),
    USER_NOT_ALLOWED_SIGNUP("User is not allowed to sign up."),
    USER_PASSWORD_MISMATCHED("The password does not match the user."),
    USER_NOT_FOUND("User is not found."),
    LOGIN_FAILURE("Login failed."),
    ENCRYPTION_ENCRYPT_FAILED("Encryption failed."),
    ENCRYPTION_DECRYPT_FAILED("Decryption failed."),
    UNAUTHORIZED_ERROR("Unauthorized"),
    FORBIDDEN_ERROR("You do not have permission to access this resource."),
    SCRAP_REQUEST_ALREADY("Scrap request already exists."),
    NOT_FOUND_TAX_BASE("Tax base is not found."),
    NOT_FOUND_USER_INCOME("No income has been found for the user.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

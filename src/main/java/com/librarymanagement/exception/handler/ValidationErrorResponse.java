package com.librarymanagement.exception.handler;

public class ValidationErrorResponse {

    private String field;
    private String errorMessage;

    public ValidationErrorResponse(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;

    }
}

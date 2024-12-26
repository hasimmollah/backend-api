package com.nutmeg.exception;



public class ApplicationSQLException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private ErrorResponse validationErrorResponse;

    public ApplicationSQLException() {
        super();
    }

    public ApplicationSQLException(String message) {
        super(message);
    }

    public ApplicationSQLException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationSQLException(Throwable cause) {
        super(cause);
    }

    public void setValidationErrorResponse(ErrorResponse validationErrorResponse) {
        this.validationErrorResponse = validationErrorResponse;

    }

    public ErrorResponse getValidationErrorResponse() {
        return validationErrorResponse;

    }
}

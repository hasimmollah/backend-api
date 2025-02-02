package com.nutmeg.exception;


public enum ErrorCodes {

   
    APPLICATION_NOT_FOUND(
        "ERR_001",
        "Application Not Found for given Id"
        ),
    APPLICATION_EXCEPTION(
        "ERR_002",
        "Application Exception occurred"
        ),
    SQL_EXCEPTION(
        "ERR_003",
        "Exception occurred while interacting with database"
        ),
    MALFORMED_REQUEST("ERR_005","Malformed request is not supported."),
    MANDATORY_FIELD("ERR_006","Mandatory Field is missing"),
    DOWNSTREAM_EXCEPTION("ERR_007","Can't connect to downstream url %s")

;

    private final String code;
    private final String description;

    private ErrorCodes(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(code, description);
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
    
    public static final String missingOrEmptyHeaderErrorMsg(String headerName) {
        return "Missing or Empty Mandatory field '" + headerName + "'";
    }

    public static final String invalidHeaderLength(String headerName, Integer validLength) {
        return "'" + headerName + "' should not be greater than size " + validLength;
    }
}
package com.platform.exchange.exception;

public enum ErrorMessage {

    USER_NOT_FOUND("The user could not be found!"),
    EXISTING_USER("An account was already created for this email!"),
    PRODUCT_NOT_FOUND("The product could not be found!"),
    OUT_OF_PRODUCTS("No product could be found!"),
    FEATURE_NOT_FOUND("The feature could not be found!"),
    MANDATORY_FEATURE_NOT_FOUND("The following feature is mandatory:"),
    NEGATIVE_PRICE("The price must be positive!"),
    INVALID_DESCRIPTION("If added, description should have a length less than 100 characters!"),
    MEETING_NOT_FOUND("The meeting could not be found!");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

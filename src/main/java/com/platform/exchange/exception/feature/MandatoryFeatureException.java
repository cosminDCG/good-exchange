package com.platform.exchange.exception.feature;

import com.platform.exchange.exception.ErrorMessage;

public class MandatoryFeatureException extends RuntimeException{

    public MandatoryFeatureException() {

    }

    public MandatoryFeatureException(ErrorMessage message, String field) {
        super(message.getMessage() + " " + field);
    }
}

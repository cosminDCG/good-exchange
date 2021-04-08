package com.platform.exchange.exception.feature;

import com.platform.exchange.exception.ErrorMessage;

public class FeatureNotFoundException extends RuntimeException {

    public FeatureNotFoundException() {

    }

    public FeatureNotFoundException(ErrorMessage message) {
        super(message.getMessage());
    }
}

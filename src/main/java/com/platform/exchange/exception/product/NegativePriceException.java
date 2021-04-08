package com.platform.exchange.exception.product;

import com.platform.exchange.exception.ErrorMessage;

public class NegativePriceException extends RuntimeException {

    public NegativePriceException() {

    }

    public NegativePriceException(ErrorMessage message) {
        super(message.getMessage());
    }
}

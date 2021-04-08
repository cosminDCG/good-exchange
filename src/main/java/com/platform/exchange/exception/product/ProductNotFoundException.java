package com.platform.exchange.exception.product;

import com.platform.exchange.exception.ErrorMessage;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {

    }

    public ProductNotFoundException(ErrorMessage message) {
        super(message.getMessage());
    }
}

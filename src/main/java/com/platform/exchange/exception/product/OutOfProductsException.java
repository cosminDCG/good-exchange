package com.platform.exchange.exception.product;

import com.platform.exchange.exception.ErrorMessage;

public class OutOfProductsException extends RuntimeException{

    public OutOfProductsException () {

    }

    public OutOfProductsException(ErrorMessage message) {
        super(message.getMessage());
    }
}

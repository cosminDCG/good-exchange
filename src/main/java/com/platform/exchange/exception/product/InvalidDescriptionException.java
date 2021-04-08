package com.platform.exchange.exception.product;

import com.platform.exchange.exception.ErrorMessage;

public class InvalidDescriptionException extends RuntimeException{

    public InvalidDescriptionException() {

    }

    public InvalidDescriptionException(ErrorMessage message) {
        super(message.getMessage());
    }
}

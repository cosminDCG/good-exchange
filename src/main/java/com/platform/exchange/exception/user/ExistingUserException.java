package com.platform.exchange.exception.user;

import com.platform.exchange.exception.ErrorMessage;

public class ExistingUserException extends RuntimeException{

    public ExistingUserException() {

    }

    public ExistingUserException(ErrorMessage message) {
        super(message.getMessage());
    }
}

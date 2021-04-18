package com.platform.exchange.exception.user;

import com.platform.exchange.exception.ErrorMessage;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
    }

    public UserNotFoundException(ErrorMessage message) {
        super(message.getMessage());
    }
}

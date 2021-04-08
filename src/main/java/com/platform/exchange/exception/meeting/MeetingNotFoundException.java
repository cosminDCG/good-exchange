package com.platform.exchange.exception.meeting;

import com.platform.exchange.exception.ErrorMessage;

public class MeetingNotFoundException extends RuntimeException{

    public MeetingNotFoundException() {}

    public MeetingNotFoundException(ErrorMessage message) {
        super(message.getMessage());
    }
}

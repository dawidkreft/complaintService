package com.complaints.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserCountryNotFoundException extends BaseException {

    public UserCountryNotFoundException(String message) {
        super(message);
    }
}

package com.complaints.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserIpNotFoundException extends BaseException {

    public UserIpNotFoundException(String message) {
        super(message);
    }
}

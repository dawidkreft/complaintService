package com.complaints.service.security;

import com.complaints.service.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenAccessException extends BaseException {

    public ForbiddenAccessException(String message) {
        super(message);
    }
}

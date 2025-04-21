package com.complaints.service.service;

import com.complaints.service.exception.UserIpNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserIpProvider {

    private final HttpServletRequest httpServletRequest;

    public String getUserIp() {
        String userIp = httpServletRequest.getHeader("X-Forwarded-For");
        if (userIp == null || userIp.isBlank()) {
            throw new UserIpNotFoundException("Missing or empty 'X-Forwarded-For' header");
        }
        return userIp;
    }
}

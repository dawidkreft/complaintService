package com.complaints.service.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsProvider {

    private final HttpServletRequest httpServletRequest;

    /**
     * Imitation of a simple user details provider that retrieves userId from the request header.
     * ⚠️ This is only a simplified placeholder used for demonstration purposes.
     * In a real-world application, especially in production or microservices environments,
     * authentication and user identification should be handled securely using mechanisms such as:
     * - JWT token parsing (e.g., with Spring Security)
     * - OAuth2 access tokens
     * - API gateways passing user context
     * This implementation may be replaced or extended with a more robust security solution.
     *
     * @return userId extracted from the "userID" header
     * @throws ForbiddenAccessException if the userId header is missing or invalid
     */
    public Long getUserId() {
        String userId = httpServletRequest.getHeader("userID");
        if (userId == null || userId.isBlank()) {
            throw new ForbiddenAccessException("Missing or empty 'userID' header");
        }
        try {
            return Long.valueOf(userId);
        } catch (NumberFormatException ex) {
            throw new ForbiddenAccessException("Invalid 'userID' format");
        }
    }
}

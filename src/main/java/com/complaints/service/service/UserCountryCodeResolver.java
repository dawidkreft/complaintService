package com.complaints.service.service;

import com.complaints.service.exception.UserCountryNotFoundException;
import com.complaints.service.service.client.IpGeolocationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCountryCodeResolver {

    private final UserIpProvider userIpProvider;
    private final IpGeolocationClient ipGeolocationClient;

    public String resolveUserCountryCode() {
        var userIp = userIpProvider.getUserIp();
        Optional<String> countryCode = ipGeolocationClient.getCountryCode(userIp);
        return countryCode.orElseThrow(
                () -> new UserCountryNotFoundException("Unable to resolve country code for IP: " + userIp));
    }
}

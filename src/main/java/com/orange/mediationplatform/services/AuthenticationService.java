package com.MyProject.mediationplatform.services;

import com.MyProject.mediationplatform.model.exceptions.ApplicationNotFoundException;
import com.MyProject.mediationplatform.common.network.customerlinks.CLinksAuthenticator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AuthenticationService {

   // @Value("#{'${clinksplatform.customerlinks.applications}'.split(',')}")
    private List<String> applications = Arrays.asList("concerto", "platform");

    private CLinksAuthenticator authenticator;

    public AuthenticationService(CLinksAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    public void authenticate(String application) {
        if (!applications.contains(application)) {
            throw new ApplicationNotFoundException(application);
        } else {
            authenticator.setCustomerLinksCredentials(application);
        }
    }

}

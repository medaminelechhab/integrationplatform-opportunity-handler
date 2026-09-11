package com.MyProject.mediationplatform.services;

import com.MyProject.mediationplatform.common.network.customerlinks.CLinksAuthenticator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles({"test", "teamMember", "clinks"})
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;
    @MockitoBean
    private CLinksAuthenticator authenticator;

    @Test
    @DisplayName("Throws an exception if application is not allowed")
    public void throwAnExceptionIfApplicationIsNotAllowed() {
        try {
            authenticationService.authenticate("test");
        } catch (Exception e) {
            Assertions.assertEquals("Unknown application: test", e.getMessage());
        }
    }

    @Test
    @DisplayName("If the application is allowed, verify the request to get another token")
    public void whenApplicationIsAllowed_requestAnotherToken() {
        authenticationService.authenticate("concerto");

        Mockito.verify(authenticator, Mockito.times(1)).setCustomerLinksCredentials("concerto");
    }

}
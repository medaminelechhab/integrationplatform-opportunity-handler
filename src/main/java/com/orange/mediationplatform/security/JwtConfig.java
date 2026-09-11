package com.MyProject.mediationplatform.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    @Value("${spring.security.jwt.header}")
    private String header;

    @Value("${spring.security.jwt.prefix}")
    private String prefix;

    @Value("${spring.security.jwt.token-validity-in-seconds}")
    private int validityInSeconds;

    @Value("${spring.security.jwt.secret}")
    private String secret;


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getValidityInSeconds() {
        return validityInSeconds;
    }

    public void setValidityInSeconds(int validityInSeconds) {
        this.validityInSeconds = validityInSeconds;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}

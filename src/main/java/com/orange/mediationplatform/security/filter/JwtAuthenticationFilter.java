package com.MyProject.mediationplatform.security.filter;

import com.MyProject.mediationplatform.security.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    JwtConfig jwtConfig;

    public JwtAuthenticationFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //1) Get the authentication Header  as it is supposed to be in the request header
        String header = httpServletRequest.getHeader(jwtConfig.getHeader());

        //2) Validate the header
        if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // If there is no token provided and hence the user won't be authenticated.
        // It's Ok. Maybe the user accessing a public path or asking for a token.

        // All secured paths that needs a token are already defined and secured in config class.
        // And If user tried to access without access token, then he won't be authenticated and an exception will be thrown.

        //3 Get the token
        String token = header.replace(jwtConfig.getPrefix(), ""); //Remove the prefix

        try {    // exceptions might be thrown in creating the claims if for example the token is expired

            // 4. Validate the token
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String subject = claims.getSubject();
            if (subject != null) {
                List<String> authorities = (List<String>) claims.get("authorities");

                // 5. Create auth object
                // UsernamePasswordAuthenticationToken: A built-in object, used by spring to represent the current authenticated / being authenticated user.
                // It needs a list of authorities, which has type of GrantedAuthority interface, where SimpleGrantedAuthority is an implementation of that interface
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        subject, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

                // 6. Authenticate the user
                // Now, user is authenticated
                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }

        } catch (Exception e) {
            // In case of failure. Make sure it's clear; so guarantee user won't be authenticated
            SecurityContextHolder.clearContext();
        }

    }

    private void verifyClaims(Claims claims) {
        String keyId = (String) claims.get("kid");
        String type = (String) claims.get("typ");
        String issuer = (String) claims.get("iss");
        String subject = (String) claims.get("sub");
        String jwtId = (String) claims.get("jti");
        String issuedAt = (String) claims.get("iat");
        String expirationTime = (String) claims.get("exp");
        String clientId = (String) claims.get("client_id");
        String scope = (String) claims.get("scope");
    }
}

package com.itechart.contacts.web.security;

import com.itechart.contacts.core.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private JwtTokenProvider tokenProvider;

    @Autowired
    public CustomAuthenticationProvider(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationToken authenticationToken = (AuthenticationToken) authentication;
        String token = authenticationToken.getToken();

        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {

            User user = tokenProvider.getUserFromJWT(token);
            authenticationToken.setAuthenticated(true);
            authenticationToken.setRole(user.getRole());
            authenticationToken.setUsername(user.getEmail());
            authenticationToken.setId(user.getId());

            return authenticationToken;
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.isAssignableFrom(authentication);
    }
}

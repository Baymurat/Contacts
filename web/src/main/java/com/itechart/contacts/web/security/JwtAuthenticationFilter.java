package com.itechart.contacts.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public JwtAuthenticationFilter() {
        super("/api/**");
        setAuthenticationSuccessHandler((request, response, authentication) -> {

        });
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String url = request.getRequestURI();
        String signUpPath = "/api/auth/signup";
        String loginPath = "/api/auth/signin";

        if (url.equals(signUpPath) || url.equals(loginPath)) {
            chain.doFilter(req, res);
        } else {
            super.doFilter(req, res, chain);
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String token = Util.getJwtFromRequest(request);

        AuthenticationToken authenticationToken = new AuthenticationToken(token);
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    /*@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getRequestURI().equals("/api/auth/signin")) {
            return null;
        } else {
            String token = Util.getJwtFromRequest(request);

            AuthenticationToken authenticationToken = new AuthenticationToken(token);
            return getAuthenticationManager().authenticate(authenticationToken);
        }
    }*/

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
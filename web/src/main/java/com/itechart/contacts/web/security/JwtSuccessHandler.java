package com.itechart.contacts.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechart.contacts.web.payload.JwtAuthenticationResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtSuccessHandler implements AuthenticationSuccessHandler {

    private JwtTokenProvider provider = com.itechart.contacts.web.config.StaticApplicationContext.getContext().getBean(JwtTokenProvider.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        String token = provider.generateToken(authentication);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(new JwtAuthenticationResponse(token));
        httpServletResponse.getWriter().write(json);
    }
}

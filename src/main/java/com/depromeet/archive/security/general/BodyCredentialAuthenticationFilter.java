package com.depromeet.archive.security.general;

import com.depromeet.archive.domain.user.command.LoginCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BodyCredentialAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objMapper;

    public BodyCredentialAuthenticationFilter(String defaultFilterProcessesUrl,
                                                 AuthenticationManager authenticationManager,
                                                 ObjectMapper mapper) {
        super(defaultFilterProcessesUrl, authenticationManager);
        this.objMapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException {
        byte[] bodyBytes = httpServletRequest.getInputStream().readAllBytes();
        LoginCommand command = objMapper.readValue(bodyBytes, LoginCommand.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(command.getEmail(), command.getPassword());
        return getAuthenticationManager().authenticate(token);
    }
}

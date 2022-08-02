package site.archive.security.general;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import site.archive.api.command.LoginCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BodyCredentialAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper mapper;

    public BodyCredentialAuthenticationFilter(String defaultFilterProcessesUrl,
                                              AuthenticationManager authenticationManager,
                                              ObjectMapper mapper) {
        super(defaultFilterProcessesUrl, authenticationManager);
        this.mapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws AuthenticationException, IOException {
        var bodyBytes = httpServletRequest.getInputStream().readAllBytes();
        var command = mapper.readValue(bodyBytes, LoginCommand.class);
        var token = new UsernamePasswordAuthenticationToken(command.getEmail(), command.getPassword());
        return getAuthenticationManager().authenticate(token);
    }

}

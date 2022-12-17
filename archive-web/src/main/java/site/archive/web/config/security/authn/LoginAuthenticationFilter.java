package site.archive.web.config.security.authn;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginAuthenticationFilter
    extends AbstractAuthenticationProcessingFilter
    implements BodyCredentialAuthenticationFilter {

    private final ObjectMapper mapper;

    public LoginAuthenticationFilter(String defaultFilterProcessesUrl,
                                     AuthenticationManager authenticationManager,
                                     ObjectMapper mapper) {
        super(defaultFilterProcessesUrl, authenticationManager);
        this.mapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws AuthenticationException, IOException {
        var token = this.extractTokenFromRequest(httpServletRequest, mapper);
        return getAuthenticationManager().authenticate(token);
    }

}

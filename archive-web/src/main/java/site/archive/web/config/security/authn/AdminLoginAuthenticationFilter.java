package site.archive.web.config.security.authn;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import site.archive.domain.user.UserRole;
import site.archive.web.config.security.common.UserPrincipal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Slf4j
public class AdminLoginAuthenticationFilter
    extends AbstractAuthenticationProcessingFilter
    implements BodyCredentialAuthenticationFilter {

    private final ObjectMapper mapper;

    public AdminLoginAuthenticationFilter(String defaultFilterProcessesUrl,
                                          AuthenticationManager authenticationManager,
                                          ObjectMapper mapper) {
        super(defaultFilterProcessesUrl, authenticationManager);
        this.mapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws AuthenticationException, IOException {
        var token = this.extractTokenFromRequest(httpServletRequest, mapper);
        var authentication = getAuthenticationManager().authenticate(token);
        verifyAdminUser(authentication);
        return authentication;
    }

    private void verifyAdminUser(Authentication authentication) {
        var principal = (UserPrincipal) authentication.getPrincipal();
        var userInfo = principal.getUserInfo();
        var userRole = userInfo.getUserRole();
        if (userRole != UserRole.ADMIN) {
            log.info("Not admin user access admin login: {} {}", userInfo.getUserId(), userInfo.getMailAddress());
            throw new BadCredentialsException("Not Admin");
        }
    }

}

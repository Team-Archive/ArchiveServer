package site.archive.web.config.security.authn;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import site.archive.dto.v1.auth.LoginCommandV1;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface BodyCredentialAuthenticationFilter {

    default Authentication extractTokenFromRequest(HttpServletRequest request, ObjectMapper mapper) throws IOException {
        var bodyBytes = request.getInputStream().readAllBytes();
        var command = mapper.readValue(bodyBytes, LoginCommandV1.class);
        return new UsernamePasswordAuthenticationToken(command.getEmail(), command.getPassword());
    }

}

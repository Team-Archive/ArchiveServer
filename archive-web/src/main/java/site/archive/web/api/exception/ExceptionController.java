package site.archive.web.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/authentication")
    public ResponseEntity<Void> authenticationExceptionEndpoint() {
        throw new AuthenticationServiceException("By Authentication Exception Controller");
    }

    @GetMapping("/authorization")
    public ResponseEntity<Void> authorizationExceptionEndpoint() {
        throw new AccessDeniedException("By Authorization Exception Controller");
    }

}

package site.archive.exception.security;

import org.springframework.security.core.AuthenticationException;
import site.archive.exception.ExceptionCode;

public class TokenNotFoundException extends AuthenticationException {

    public TokenNotFoundException() {
        super(ExceptionCode.TOKEN_NOT_FOUND.getMessage());
    }

}

package site.archive.exception.security;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;
import site.archive.exception.BaseException;

@Getter
public class WrappingAuthenticationException extends AuthenticationException {

    private final BaseException cause;

    public WrappingAuthenticationException(BaseException cause) {
        super(cause.getMessage());
        this.cause = cause;
    }

}

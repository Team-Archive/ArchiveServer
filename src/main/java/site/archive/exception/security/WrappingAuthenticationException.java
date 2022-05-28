package site.archive.exception.security;

import site.archive.exception.BaseException;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class WrappingAuthenticationException extends AuthenticationException {

    private final BaseException cause;

    public WrappingAuthenticationException(BaseException cause) {
        super(cause.getMessage());
        this.cause = cause;
    }

}

package site.archive.common.exception.security;

import lombok.Getter;
import site.archive.common.exception.BaseException;

@Getter
public class WrappingAuthenticationException extends RuntimeException {

    private final BaseException cause;

    public WrappingAuthenticationException(BaseException cause) {
        super(cause.getMessage());
        this.cause = cause;
    }

}

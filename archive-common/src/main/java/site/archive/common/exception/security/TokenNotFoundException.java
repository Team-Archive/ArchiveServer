package site.archive.common.exception.security;

import site.archive.common.exception.ExceptionCode;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException() {
        super(ExceptionCode.TOKEN_NOT_FOUND.getMessage());
    }

}

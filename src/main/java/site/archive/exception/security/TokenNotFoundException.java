package site.archive.exception.security;

import site.archive.exception.BaseException;
import site.archive.exception.ExceptionCode;

public class TokenNotFoundException extends BaseException {

    public TokenNotFoundException() {
        super(ExceptionCode.TOKEN_NOT_FOUND);
    }

}

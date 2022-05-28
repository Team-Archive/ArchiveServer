package site.archive.exception.security;

import site.archive.exception.BaseException;
import site.archive.exception.ExceptionCode;

public class InvalidTokenException extends BaseException {

    public InvalidTokenException() {
        super(ExceptionCode.INVALID_TOKEN);
    }

}

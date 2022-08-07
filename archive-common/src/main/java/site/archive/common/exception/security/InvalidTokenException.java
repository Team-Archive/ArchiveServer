package site.archive.common.exception.security;

import site.archive.common.exception.BaseException;
import site.archive.common.exception.ExceptionCode;

public class InvalidTokenException extends BaseException {

    public InvalidTokenException() {
        super(ExceptionCode.INVALID_TOKEN);
    }

}

package site.archive.exception.common;

import site.archive.exception.BaseException;
import site.archive.exception.ExceptionCode;

public class UnauthorizedResourceException extends BaseException {

    public UnauthorizedResourceException(String message) {
        super(message, ExceptionCode.UNAUTHORIZED_RESOURCE);
    }

    public UnauthorizedResourceException(String message, String resource) {
        super(String.format("%s[%s]", message, resource),
              ExceptionCode.UNAUTHORIZED_RESOURCE);
    }

}

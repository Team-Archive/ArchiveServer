package site.archive.common.exception.common;

import site.archive.common.exception.BaseException;
import site.archive.common.exception.ExceptionCode;

public class UnauthorizedResourceException extends BaseException {

    public UnauthorizedResourceException(String message) {
        super(message, ExceptionCode.UNAUTHORIZED_RESOURCE);
    }

    public UnauthorizedResourceException(String message, String resource) {
        super(String.format("%s[%s]", message, resource),
              ExceptionCode.UNAUTHORIZED_RESOURCE);
    }

}

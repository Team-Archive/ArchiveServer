package site.archive.common.exception.common;

import site.archive.common.exception.BaseException;
import site.archive.common.exception.ExceptionCode;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String notFoundMessage) {
        super(notFoundMessage, ExceptionCode.NOT_FOUND_RESOURCE);
    }

    public ResourceNotFoundException(String notFoundMessage, String notFoundResource) {
        super(String.format("%s[%s]", notFoundMessage, notFoundResource),
              ExceptionCode.NOT_FOUND_RESOURCE);
    }

}

package com.depromeet.archive.exception.common;

import com.depromeet.archive.exception.BaseException;
import com.depromeet.archive.exception.ExceptionCode;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String notFoundMessage) {
        super(notFoundMessage, ExceptionCode.NOT_FOUND_RESOURCE);
    }

    public ResourceNotFoundException(String notFoundMessage, String notFoundResource) {
        super(String.format("%s[%s]", notFoundMessage, notFoundResource),
                ExceptionCode.NOT_FOUND_RESOURCE);
    }

}

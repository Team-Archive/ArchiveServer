package com.depromeet.archive.exception.common;

import com.depromeet.archive.exception.BaseException;
import com.depromeet.archive.exception.ExceptionCode;

public class DuplicateResourceException extends BaseException {

    public DuplicateResourceException(String duplicatedResource) {
        super(duplicatedResource, ExceptionCode.DUPLICATED_RESOURCE);
    }

}

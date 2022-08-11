package site.archive.common.exception.common;

import site.archive.common.exception.BaseException;
import site.archive.common.exception.ExceptionCode;

public class DuplicateResourceException extends BaseException {

    public DuplicateResourceException(String duplicatedResource) {
        super(duplicatedResource, ExceptionCode.DUPLICATED_RESOURCE);
    }

}

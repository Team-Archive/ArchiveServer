package site.archive.exception.common;

import site.archive.exception.BaseException;
import site.archive.exception.ExceptionCode;

public class DuplicateResourceException extends BaseException {

    public DuplicateResourceException(String duplicatedResource) {
        super(duplicatedResource, ExceptionCode.DUPLICATED_RESOURCE);
    }

}

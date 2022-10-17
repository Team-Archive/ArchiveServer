package site.archive.common.exception.common;

import site.archive.common.exception.BaseException;
import site.archive.common.exception.ExceptionCode;

public class DuplicateFieldValueException extends BaseException {

    private static final String DUPLICATED_FILED_MESSAGE = "'%s'은 중복된 %s 입니다. 다른 %s을 사용해주세요.";

    public DuplicateFieldValueException(String duplicatedField, String duplicatedValue) {
        super(DUPLICATED_FILED_MESSAGE.formatted(duplicatedValue, duplicatedField, duplicatedField),
              ExceptionCode.DUPLICATED_FIELD_VALUE);
    }

}

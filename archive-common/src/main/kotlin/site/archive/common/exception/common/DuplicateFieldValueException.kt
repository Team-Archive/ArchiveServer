package site.archive.common.exception.common

import site.archive.common.exception.BaseException
import site.archive.common.exception.ExceptionCode

class DuplicateFieldValueException(duplicatedField: String, duplicatedValue: String) :
    BaseException(
        "'$duplicatedValue'은 중복된 $duplicatedField 입니다. 다른 $duplicatedField 을 사용해주세요",
        ExceptionCode.DUPLICATED_FIELD_VALUE
    )
package site.archive.common.exception.common

import site.archive.common.exception.BaseException
import site.archive.common.exception.ExceptionCode

class DuplicateResourceException(duplicatedResource: String) : BaseException(duplicatedResource, ExceptionCode.DUPLICATED_RESOURCE)

package site.archive.common.exception.common

import site.archive.common.exception.BaseException
import site.archive.common.exception.ExceptionCode

class ResourceNotFoundException(notFoundMessage: String) : BaseException(notFoundMessage, ExceptionCode.NOT_FOUND_RESOURCE)

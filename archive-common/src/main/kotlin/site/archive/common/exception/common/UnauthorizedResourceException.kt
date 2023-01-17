package site.archive.common.exception.common

import site.archive.common.exception.BaseException
import site.archive.common.exception.ExceptionCode

class UnauthorizedResourceException(message: String) : BaseException(message, ExceptionCode.UNAUTHORIZED_RESOURCE)

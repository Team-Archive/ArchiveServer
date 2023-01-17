package site.archive.common.exception.security

import site.archive.common.exception.BaseException
import site.archive.common.exception.ExceptionCode

class InvalidTokenException : BaseException(ExceptionCode.INVALID_TOKEN)
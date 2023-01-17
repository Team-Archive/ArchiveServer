package site.archive.common.exception.security

import site.archive.common.exception.BaseException
import site.archive.common.exception.ExceptionCode

class TokenNotFoundException : BaseException(ExceptionCode.TOKEN_NOT_FOUND)

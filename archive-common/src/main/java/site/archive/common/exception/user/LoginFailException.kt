package site.archive.common.exception.user

import site.archive.common.exception.BaseException
import site.archive.common.exception.ExceptionCode

class LoginFailException(message: String) : BaseException(message, ExceptionCode.LOGIN_FAIL)

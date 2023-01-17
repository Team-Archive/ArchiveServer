package site.archive.common.exception.user

import site.archive.common.exception.BaseException
import site.archive.common.exception.ExceptionCode

class OAuthUserHasNotPasswordException : BaseException(ExceptionCode.OAUTH_USER_NOT_HAS_PASSWORD) {
}
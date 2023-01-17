package site.archive.common.exception.user

import site.archive.common.exception.BaseException
import site.archive.common.exception.ExceptionCode

class OAuthRegisterFailException(oAuthProviderRegistrationId: String, message: String) :
    BaseException("$oAuthProviderRegistrationId : $message", ExceptionCode.REGISTER_FAIL)

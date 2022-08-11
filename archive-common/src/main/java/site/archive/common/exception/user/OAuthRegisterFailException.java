package site.archive.common.exception.user;

import site.archive.common.exception.BaseException;
import site.archive.common.exception.ExceptionCode;

public class OAuthRegisterFailException extends BaseException {

    public OAuthRegisterFailException(String oAuthProviderRegistrationId, String message) {
        super(String.format("%s : %s", oAuthProviderRegistrationId, message),
              ExceptionCode.REGISTER_FAIL);
    }

}

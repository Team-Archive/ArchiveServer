package site.archive.exception.user;

import site.archive.domain.user.entity.OAuthProvider;
import site.archive.exception.BaseException;
import site.archive.exception.ExceptionCode;

public class OAuthRegisterFailException extends BaseException {

    public OAuthRegisterFailException(OAuthProvider oAuthProvider, String message) {
        super(String.format("%s : %s", oAuthProvider.getRegistrationId(), message),
              ExceptionCode.REGISTER_FAIL);
    }

}

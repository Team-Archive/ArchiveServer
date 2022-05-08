package com.depromeet.archive.exception.user;

import com.depromeet.archive.domain.user.entity.OAuthProvider;
import com.depromeet.archive.exception.BaseException;
import com.depromeet.archive.exception.ExceptionCode;

public class OAuthRegisterFailException extends BaseException {

    public OAuthRegisterFailException(OAuthProvider oAuthProvider, String message) {
        super(String.format("%s : %s", oAuthProvider.getRegistrationId(), message),
              ExceptionCode.REGISTER_FAIL);
    }

}

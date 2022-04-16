package com.depromeet.archive.exception.user;

import com.depromeet.archive.exception.BaseException;
import com.depromeet.archive.exception.ExceptionCode;

public class OAuthUserHasNotPasswordException extends BaseException {

    public OAuthUserHasNotPasswordException() {
        super(ExceptionCode.OAUTH_USER_NOT_HAS_PASSWORD);
    }

}

package site.archive.common.exception.user;

import site.archive.common.exception.BaseException;
import site.archive.common.exception.ExceptionCode;

public class OAuthUserHasNotPasswordException extends BaseException {

    public OAuthUserHasNotPasswordException() {
        super(ExceptionCode.OAUTH_USER_NOT_HAS_PASSWORD);
    }

}

package site.archive.exception.user;

import site.archive.exception.BaseException;
import site.archive.exception.ExceptionCode;

public class OAuthUserHasNotPasswordException extends BaseException {

    public OAuthUserHasNotPasswordException() {
        super(ExceptionCode.OAUTH_USER_NOT_HAS_PASSWORD);
    }

}

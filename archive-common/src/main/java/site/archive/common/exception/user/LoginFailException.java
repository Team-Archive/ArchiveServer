package site.archive.common.exception.user;

import site.archive.common.exception.BaseException;
import site.archive.common.exception.ExceptionCode;

public class LoginFailException extends BaseException {

    public LoginFailException(String message) {
        super(message, ExceptionCode.LOGIN_FAIL);
    }

}

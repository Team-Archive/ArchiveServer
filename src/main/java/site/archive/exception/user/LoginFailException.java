package site.archive.exception.user;

import site.archive.exception.BaseException;
import site.archive.exception.ExceptionCode;

public class LoginFailException extends BaseException {

    public LoginFailException(String message) {
        super(message, ExceptionCode.LOGIN_FAIL);
    }

}

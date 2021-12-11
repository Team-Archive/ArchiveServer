package com.depromeet.archive.exception.user;

import com.depromeet.archive.exception.BaseException;
import com.depromeet.archive.exception.ExceptionCode;

public class LoginFailException extends BaseException {

    public LoginFailException(String message) {
        super(message, ExceptionCode.LOGIN_FAIL);
    }

}

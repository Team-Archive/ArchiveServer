package com.depromeet.archive.domain.user.exception;


import com.depromeet.archive.common.exception.BaseException;

public class LoginFailException extends BaseException {

    public LoginFailException(String msg) {
        super(msg);
    }
}

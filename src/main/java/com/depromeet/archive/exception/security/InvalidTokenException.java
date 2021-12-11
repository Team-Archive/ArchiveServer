package com.depromeet.archive.exception.security;

import com.depromeet.archive.exception.BaseException;
import com.depromeet.archive.exception.ExceptionCode;

public class InvalidTokenException extends BaseException {

    public InvalidTokenException() {
        super(ExceptionCode.INVALID_TOKEN);
    }

}

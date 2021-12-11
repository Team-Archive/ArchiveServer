package com.depromeet.archive.exception.security;

import com.depromeet.archive.exception.BaseException;
import com.depromeet.archive.exception.ExceptionCode;

public class TokenNotFoundException extends BaseException {

    public TokenNotFoundException() {
        super(ExceptionCode.TOKEN_NOT_FOUND);
    }

}

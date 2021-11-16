package com.depromeet.archive.security.exception;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(String msg) {
        super(msg);
    }

}

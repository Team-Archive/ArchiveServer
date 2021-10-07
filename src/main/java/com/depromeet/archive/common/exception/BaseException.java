package com.depromeet.archive.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BaseException extends RuntimeException {

    public BaseException(String msg) {
        super(msg);
    }
}

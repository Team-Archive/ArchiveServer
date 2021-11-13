package com.depromeet.archive.infra.cloud.aws;

import com.depromeet.archive.common.exception.BaseException;

public class FileInvalidException extends BaseException {

    public FileInvalidException(String message) {
        super(message);
    }

}

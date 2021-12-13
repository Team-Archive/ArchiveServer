package com.depromeet.archive.exception.infra;

import com.depromeet.archive.exception.BaseException;
import com.depromeet.archive.exception.ExceptionCode;

public class FileInvalidException extends BaseException {

    public FileInvalidException() {
        super(ExceptionCode.FAILED_FILE_UPLOAD);
    }

}

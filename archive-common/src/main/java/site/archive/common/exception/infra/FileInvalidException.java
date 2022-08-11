package site.archive.common.exception.infra;

import site.archive.common.exception.BaseException;
import site.archive.common.exception.ExceptionCode;

public class FileInvalidException extends BaseException {

    public FileInvalidException() {
        super(ExceptionCode.FAILED_FILE_UPLOAD);
    }

}

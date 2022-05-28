package site.archive.exception.infra;

import site.archive.exception.BaseException;
import site.archive.exception.ExceptionCode;

public class FileInvalidException extends BaseException {

    public FileInvalidException() {
        super(ExceptionCode.FAILED_FILE_UPLOAD);
    }

}

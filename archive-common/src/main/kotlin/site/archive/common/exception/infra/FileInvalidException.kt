package site.archive.common.exception.infra

import site.archive.common.exception.BaseException
import site.archive.common.exception.ExceptionCode

class FileInvalidException : BaseException(ExceptionCode.FAILED_FILE_UPLOAD)

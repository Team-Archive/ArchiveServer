package site.archive.common.exception.infra

import site.archive.common.exception.BaseException
import site.archive.common.exception.ExceptionCode

class SlackException : BaseException(ExceptionCode.SLACK_MESSAGE_FAILED)

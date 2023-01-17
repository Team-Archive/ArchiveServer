package site.archive.common.exception

import java.util.Optional

open class BaseException : RuntimeException {

    val exceptionCode: ExceptionCode
    private var additionalMessage: String? = null

    constructor(exceptionCode: ExceptionCode) : super(exceptionCode.message) {
        this.exceptionCode = exceptionCode
    }

    constructor(additionalMessage: String?, exceptionCode: ExceptionCode) : super("${exceptionCode.message} : $additionalMessage") {
        this.exceptionCode = exceptionCode
        this.additionalMessage = additionalMessage
    }

    fun getAdditionalMessage(): Optional<String> {
        return Optional.ofNullable(additionalMessage)
    }

}
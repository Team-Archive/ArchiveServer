package site.archive.exception;

import java.util.Optional;

public class BaseException extends RuntimeException {

    private final ExceptionCode exceptionCode;
    private String additionalMessage;

    public BaseException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public BaseException(String additionalMessage, ExceptionCode exceptionCode) {
        super(String.format("%s : %s", exceptionCode.getMessage(), additionalMessage));
        this.exceptionCode = exceptionCode;
        this.additionalMessage = additionalMessage;
    }

    public ExceptionCode getErrorCode() {
        return exceptionCode;
    }

    public Optional<String> getAdditionalMessage() {
        return Optional.ofNullable(additionalMessage);
    }

}

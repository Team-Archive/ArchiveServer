package site.archive.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExceptionResponse {

    private String message;
    private HttpStatus status;
    private String code;

    private ExceptionResponse(final ExceptionCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
    }

    private ExceptionResponse(String message, HttpStatus status, String code) {
        this.message = message;
        this.status = status;
        this.code = code;
    }

    public static ExceptionResponse of(final ExceptionCode code) {
        return new ExceptionResponse(code);
    }

    public static ExceptionResponse of(final ExceptionCode code, final String additionalMessage) {
        var message = String.format("%s : %s", code.getMessage(), additionalMessage);
        return new ExceptionResponse(message, code.getStatus(), code.getCode());
    }

    public static ExceptionResponse of(String message, HttpStatus status, String code) {
        return new ExceptionResponse(message, status, code);
    }

}
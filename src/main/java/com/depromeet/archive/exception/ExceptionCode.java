package com.depromeet.archive.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExceptionCode {

    // Global
    NO_VALUE(HttpStatus.BAD_REQUEST, "Global001", "필요한 값이 없거나 문제가 없습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Global002", "Invalid Input Value"),
    TYPE_MISMATCH_VALUE(HttpStatus.BAD_REQUEST, "Global003", "타입(enum)이 일치하지 않습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Global004", "Invalid Input Value"),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "Global005", "Access is Denied"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Global006", "Server Error"),

    // Infra
    FAILED_FILE_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR, "Infra001", "(File IO) Failed to stream of upload file"),

    // Common
    DUPLICATED_RESOURCE(HttpStatus.BAD_REQUEST, "Common001", "이미 존재하는 리소스 입니다"),
    NOT_FOUND_RESOURCE(HttpStatus.BAD_REQUEST, "Common002", "존재 하지 않는 리소스 입니다"),

    // User
    LOGIN_FAIL(HttpStatus.BAD_REQUEST, "User001", "로그인에 실패했습니다"),

    // Security
    TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "Security001", "`Authorization` 헤더에 토큰이 존재하지 않습니다"),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "Security002", "토큰 정보가 올바르지 않았습니다"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    ExceptionCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
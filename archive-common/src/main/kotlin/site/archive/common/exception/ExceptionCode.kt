package site.archive.common.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ExceptionCode(val status: HttpStatus,
                         val code: String,
                         val message: String) {

    // Global
    NO_VALUE(HttpStatus.BAD_REQUEST, "Global001", "필요한 값이 없거나 전달된 값에 문제가 있습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Global002", "Invalid Input Value"),
    TYPE_MISMATCH_VALUE(HttpStatus.BAD_REQUEST, "Global003", "타입(enum)이 일치하지 않습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Global004", "Invalid Input Value"),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "Global005", "Access is Denied"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Global006", "Server Error"),

    // Infra
    FAILED_FILE_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR, "Infra001", "(File IO) Failed to stream of upload file"),
    SLACK_MESSAGE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Infra002", "Sending slack message failed"),

    // Common
    DUPLICATED_RESOURCE(HttpStatus.CONFLICT, "Common001", "이미 존재하는 리소스 입니다"),
    NOT_FOUND_RESOURCE(HttpStatus.BAD_REQUEST, "Common002", "존재 하지 않는 리소스 입니다"),
    UNAUTHORIZED_RESOURCE(HttpStatus.FORBIDDEN, "Common003", "리소스에 대한 접근권한이 없습니다"),
    DUPLICATED_FIELD_VALUE(HttpStatus.BAD_REQUEST, "Common004", "중복된 값 입니다"),

    // User
    LOGIN_FAIL(HttpStatus.BAD_REQUEST, "User001", "로그인에 실패했습니다"),
    REGISTER_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "User002", "회원가입에 실패했습니다"),
    OAUTH_USER_NOT_HAS_PASSWORD(HttpStatus.FORBIDDEN, "User003", "소셜 로그인 회원은 비밀번호 찾기를 할 수 없습니다"),

    // Security
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "Security001", "`Authorization` 헤더에 토큰이 존재하지 않거나, 잘못된 토큰입니다"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Security002", "토큰 정보가 올바르지 않았습니다"),
    AUTHENTICATION_FAILURE(HttpStatus.UNAUTHORIZED, "Security003", "인증에 실패하였습니다"),
    ;

}
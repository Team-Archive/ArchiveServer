package com.depromeet.archive.exception;

import com.depromeet.archive.exception.common.DuplicateResourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Optional;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(DuplicateResourceException.class)
    protected ResponseEntity<ExceptionResponse> handleDuplicateResourceException(DuplicateResourceException e) {
        log.warn("duplicateResourceException", e);
        ExceptionCode errorCode = ExceptionCode.DUPLICATED_RESOURCE;
        final var response = e.getAdditionalMessage()
                              .map(additionalMessage -> ExceptionResponse.of(errorCode, additionalMessage))
                              .orElse(ExceptionResponse.of(errorCode));
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        final var errMsg = makeErrorMessage(e);
        final var response = errMsg
            .map((err) -> ExceptionResponse.of(ExceptionCode.NO_VALUE, err))
            .orElse(ExceptionResponse.of(ExceptionCode.NO_VALUE));
        return new ResponseEntity<>(response, ExceptionCode.NO_VALUE.getStatus());
    }

    private Optional<String> makeErrorMessage(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getAllErrors();
        if (errors.isEmpty()) {return Optional.empty();}
        return Optional.ofNullable(errors.get(0).getDefaultMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ExceptionResponse> missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException", e);
        final var response = ExceptionResponse.of(ExceptionCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, ExceptionCode.INVALID_INPUT_VALUE.getStatus());
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ExceptionResponse> handleBindException(BindException e) {
        log.error("handleBindException", e);
        final var response = ExceptionResponse.of(ExceptionCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, ExceptionCode.INVALID_INPUT_VALUE.getStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        final var response = ExceptionResponse.of(ExceptionCode.TYPE_MISMATCH_VALUE);
        return new ResponseEntity<>(response, ExceptionCode.TYPE_MISMATCH_VALUE.getStatus());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        final var response = ExceptionResponse.of(ExceptionCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, ExceptionCode.METHOD_NOT_ALLOWED.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        final var response = ExceptionResponse.of(ExceptionCode.HANDLE_ACCESS_DENIED);
        return new ResponseEntity<>(response, ExceptionCode.HANDLE_ACCESS_DENIED.getStatus());
    }

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ExceptionResponse> handleBaseException(final BaseException e) {
        log.error("BaseException ", e);
        final var errorCode = e.getErrorCode();
        final var response = e.getAdditionalMessage()
                              .map(additionalMessage -> ExceptionResponse.of(errorCode, additionalMessage))
                              .orElse(ExceptionResponse.of(errorCode));
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error("Exception (Unexpected) ", e);
        var response = ExceptionResponse.of(ExceptionCode.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, ExceptionCode.INTERNAL_SERVER_ERROR.getStatus());
    }

}
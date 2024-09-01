package com.letscareer.global.exception;

import com.letscareer.global.handler.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 공통적으로 사용하는 ErrorResponse 빌더 메서드
    private ResponseEntity<ErrorResponse> generateErrorResponse(HttpStatus status, String message) {
        ErrorResponse errorResponse = ErrorResponse.of(status, message, null);
        return new ResponseEntity<>(errorResponse, status);
    }

    //커스텀 Exception
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException exception) {
        log.error("Exception occurred: {}\n", exception.getMessage());
        return generateErrorResponse(exception.getContent().getHttpStatus(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
        log.error("Unhandled exception occurred: {}\n", e.getMessage(), e);
        return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(HttpStatusException.class)
    public ResponseEntity<ErrorResponse> handleHttpStatusException(HttpStatusException e) {
        return generateErrorResponse(HttpStatus.valueOf(e.getStatusCode()), e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        log.error("No such element found: {}\n", e.getMessage(), e);
        return generateErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        log.error("Empty result for data access operation: {}\n", e.getMessage(), e);
        return generateErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonException(HttpMessageNotReadableException e) {
        log.error("Json Exception ErrMessage={}\n", e.getMessage());
        return generateErrorResponse(HttpStatus.BAD_REQUEST,"json 형식이 올바르지 않습니다.");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleRequestMethodException(HttpRequestMethodNotSupportedException e) {
        log.error("Http Method not supported Exception ErrMessage={}\n", e.getMessage());
        return generateErrorResponse(HttpStatus.BAD_REQUEST,"해당 요청에 대한 API가 존재하지 않습니다. 엔드 포인트를 확인해주시길 바랍니다.");
    }

    // 다른 특정 예외 핸들러 추가 가능



}

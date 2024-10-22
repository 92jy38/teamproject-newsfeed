package com.sparta.newsfeed_project.domain.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 프로젝트의 모든 컨트롤러가 발생시킬 수 있는 요청에 대한 예외를 처리하는 전역 예외처리기입니다.
 *
 * @since 2024-10-22
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    // 별도 처리기가 지정되지 않은 RuntimeException 에 대해, Internal server error 를 반환
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage(),ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    // 사용자 지정 예외 처리 예시
    @ExceptionHandler(CustomRequestException.class)
    public ResponseEntity<String> handleCustomRequestException(CustomRequestException ex) {
        log.error(ex.getMessage(),ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}

package com.sparta.newsfeed_project.domain.common.exception;

import com.sparta.newsfeed_project.domain.common.dto.ResponseStatusDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * 예외 처리 클래스.
 *
 * @since 2024-10-21
 */
@Slf4j(topic = "exception:")
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 입력 관련 예외 처리
     *
     * @since 2024-10-22
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseStatusDto> BaseException(MethodArgumentNotValidException ex) {
        // 에러 메시지 추출
        String errorMsg = ex.getBindingResult().
                getAllErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseStatusDto(ResponseCode.BAD_INPUT, errorMsg));
    }

    /**
     * 이미지 업로드/다운로드 시 발생할 수 있는 예외 처리
     *
     * @since 2024-10-23
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseStatusDto> BaseException(IOException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseStatusDto(ResponseCode.BAD_INPUT, ex.getMessage()));
    }

    /**
     * ResponseException 예외 처리
     *
     * @since 2024-10-22
     */
    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ResponseStatusDto> BaseException(ResponseException ex) {
        return ResponseEntity
                .status(ex.getResponseCode().getHttpStatus())
                .body(new ResponseStatusDto(ex.getResponseCode()));
    }

    /**
     * 그 외 기타 예외처리
     *
     * @since 2024-10-22
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStatusDto> BaseException(Exception ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(ResponseCode.UNKNOWN_ERROR.getHttpStatus())
                .body(new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR));
    }
}

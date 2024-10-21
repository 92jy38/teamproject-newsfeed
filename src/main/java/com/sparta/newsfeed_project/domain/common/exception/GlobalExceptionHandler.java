package com.sparta.newsfeed_project.domain.common.exception;

import com.sparta.newsfeed_project.domain.common.dto.ResponseStatusDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 입력 관련 예외 처리 클래스.
 *
 * @since 2024-10-21
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseStatusDto> BaseException(MethodArgumentNotValidException e) {
        // 에러 메시지 추출
        String errorMsg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        // 적절한 BaseResponseStatus 사용
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseStatusDto(ResponseCode.BAD_INPUT, errorMsg));
    }
}

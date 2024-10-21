package com.sparta.newsfeed_project.domain.common.dto;

import com.sparta.newsfeed_project.domain.common.exception.ResponseCode;
import org.springframework.http.HttpStatus;

/**
 * API응답 상태에 대한 정보를 제공하는 DTO 클래스
 *
 * @since 2024-10-21
 */
public class ResponseStatusDto {
    private final HttpStatus state;
    private final String message;

    public ResponseStatusDto(ResponseCode responseCode) {
        state = responseCode.getHttpStatus();
        message = responseCode.getMessage();
    }

    public ResponseStatusDto(ResponseCode responseCode, String message) {
        state = responseCode.getHttpStatus();
        this.message = responseCode.getMessage() + ": " + message;
    }
}

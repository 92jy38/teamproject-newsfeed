package com.sparta.newsfeed_project.domain.common.exception;

import lombok.Getter;

/**
 * API 응답 시 발생하는 예외를 나타내는 class
 *
 * @since 2024-10-03
 */
@Getter
public class ResponseException extends RuntimeException {
    private final ResponseCode responseCode;
    private final String message;

    public ResponseException(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.message = responseCode.getMessage();
    }

    public ResponseException(ResponseCode responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }
}

package com.sparta.newsfeed_project.domain.common.exception;

public class CustomRequestException extends RuntimeException {
    public CustomRequestException(String message) {
        super(message);
    }
}

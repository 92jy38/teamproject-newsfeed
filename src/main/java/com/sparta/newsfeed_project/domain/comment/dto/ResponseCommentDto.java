package com.sparta.newsfeed_project.domain.comment.dto;

import com.sparta.newsfeed_project.domain.comment.entity.Comment;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ResponseCommentDto {
    // username 일지 nickname 일지 멤머 담당자와 상의
    private final Long id;
    private final String content;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ResponseCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getMember().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
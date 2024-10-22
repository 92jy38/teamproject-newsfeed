package com.sparta.newsfeed_project.domain.comment.dto;

import com.sparta.newsfeed_project.domain.comment.entity.Comment;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ResponseCommentDto {
    private final Long id;
    private final String content;
    private final String username;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;

    public ResponseCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getMember().getUsername();
        this.createAt = comment.getCreateAt();
        this.updateAt = comment.getUpdateAt();
    }
}
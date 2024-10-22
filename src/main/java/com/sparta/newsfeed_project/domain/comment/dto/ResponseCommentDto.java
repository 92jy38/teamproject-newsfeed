package com.sparta.newsfeed_project.domain.comment.dto;

import com.sparta.newsfeed_project.domain.comment.entity.Comment;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class ResponseCommentDto {

    private final Long id;
    private final String content;
    private final String username;
//    private final Long commentId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    //user? member?////////////////////////////////////////////
    public ResponseCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getMember().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        //this.commentId = comment.getComment().getId(); 고민중///////////////////////////////
    }
}
package com.sparta.newsfeed_project.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RequestCommentDto {

    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(min = 1, max = 255, message = "댓글은 1자 이상 255자 이하만 가능합니다")
    private String content;

//    @NotNull(message = "작성자 ID는 필수입니다.")
//    private Long memberId;
}
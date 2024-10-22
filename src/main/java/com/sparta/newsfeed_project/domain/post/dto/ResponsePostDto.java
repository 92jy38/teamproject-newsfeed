package com.sparta.newsfeed_project.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ResponsePostDto {

    private Long id;
    private String caption;
    private String imgUrl;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}

package com.sparta.newsfeed_project.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestPostDto {

    @NotBlank
    private String imgData;

    @NotBlank
    @Size(max = 255)
    private String caption;


}

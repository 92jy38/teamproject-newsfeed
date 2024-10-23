package com.sparta.newsfeed_project.domain.post.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RequestPostDeleteDto {

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,50}$",
            message = "비밀번호는 [8 ~ 50]글자 이내이며, [영문 + 숫자 + 특수문자]를 최소 1글자씩 포함해야 합니다.")
    private String password;
}

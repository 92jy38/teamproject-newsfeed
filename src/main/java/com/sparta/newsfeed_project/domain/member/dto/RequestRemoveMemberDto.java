package com.sparta.newsfeed_project.domain.member.dto;

import com.sparta.newsfeed_project.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 유저 삭제 요청 DTO 클래스
 *
 * @since 2024-10-21
 */
@Getter
@NoArgsConstructor
public class RequestRemoveMemberDto {
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,50}$",
            message = "비밀번호는 [8 ~ 50]글자 이내이며, [영문 + 숫자 + 특수문자]를 최소 1글자씩 포함해야 합니다.")
    private String password;
}

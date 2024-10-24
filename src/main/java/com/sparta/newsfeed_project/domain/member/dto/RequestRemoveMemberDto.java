package com.sparta.newsfeed_project.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 삭제 요청 DTO 클래스
 *
 * @since 2024-10-21
 */
@Getter
@NoArgsConstructor
public class RequestRemoveMemberDto {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}

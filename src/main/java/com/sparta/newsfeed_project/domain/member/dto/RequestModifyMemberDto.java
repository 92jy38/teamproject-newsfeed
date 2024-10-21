package com.sparta.newsfeed_project.domain.member.dto;

import com.sparta.newsfeed_project.domain.member.entity.Member;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 유저 수정 요청 DTO 클래스
 *
 * @since 2024-10-21
 */
@Getter
@Setter
@NoArgsConstructor
public class RequestModifyMemberDto {
    @Email(message = "올바른 형식의 이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 50, message = "비밀번호는 8~50자 사이입니다.")
    private String password;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 50, message = "비밀번호는 8~50자 사이입니다.")
    private String newPassword;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Max(value = 50, message = "닉네임은 50자까지 입력할 수 있습니다.")
    private String nickname;

    @NotBlank(message = "이름을 입력해주세요.")
    @Max(value = 50, message = "이름은 50자까지 입력할 수 있습니다.")
    private String username;

    @NotBlank(message = "소개글을 입력해주세요.")
    @Max(value = 255, message = "이름은 255자까지 입력할 수 있습니다.")
    private String introduce;

    /**
     * DTO 객체를 Member 엔티티 객체로 변환합니다.
     *
     * @param memberDto 수정할 정보를 담은 DTO
     * @return 생성된 Member 엔티티 객체
     * @since 2024-10-21
     */
    public Member from(RequestModifyMemberDto memberDto, Long id, String password) {
        return Member.builder()
                .id(id)
                .email(memberDto.getEmail())
                .password(password)
                .username(memberDto.getUsername())
                .nickname(memberDto.getNickname())
                .introduce(memberDto.getIntroduce()).build();
    }
}

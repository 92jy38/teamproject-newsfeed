package com.sparta.newsfeed_project.domain.member.dto;

import com.sparta.newsfeed_project.domain.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 요청 DTO 클래스
 *
 * @since 2024-10-21
 */
@Getter
@NoArgsConstructor
public class RequestCreateMemberDto {
    @Email(message = "올바른 형식의 이메일을 입력해주세요")
    private String email;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,50}$",
            message = "비밀번호는 [8 ~ 50]글자 이내이며, [영문 + 숫자 + 특수문자]를 최소 1글자씩 포함해야 합니다.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요")
    @Size(max = 50, message = "닉네임은 50자까지 입력할 수 있습니다.")
    private String nickname;

    @NotBlank(message = "이름을 입력해주세요")
    @Size(max = 50, message = "이름은 50자까지 입력할 수 있습니다.")
    private String username;

    /**
     * DTO 객체를 Member 엔티티 객체로 변환합니다.
     *
     * @param memberDto 회원 가입 요청 DTO
     * @return 생성된 User 엔티티 객체
     * @since 2024-10-21
     */
    public Member from(RequestCreateMemberDto memberDto, String password) {
        return Member.builder()
                .password(password)
                .email(memberDto.getEmail())
                .username(memberDto.getUsername())
                .nickname(memberDto.getNickname()).build();
    }
}

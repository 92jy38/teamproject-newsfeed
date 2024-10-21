package com.sparta.newsfeed_project.domain.member.dto;

import com.sparta.newsfeed_project.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 8, max = 50, message = "비밀번호는 8~50자 사이입니다.")
    private String password;

    /**
     * DTO 객체를 Member 엔티티 객체로 변환합니다.
     *
     * @param requestDto 삭제 요청 DTO
     * @return 생성된 Member 엔티티 객체
     * @since 2024-10-21
     */
    public Member from(RequestRemoveMemberDto requestDto, Long id) {
        return Member.builder()
                .id(id)
                .password(requestDto.getPassword()).build();
    }
}

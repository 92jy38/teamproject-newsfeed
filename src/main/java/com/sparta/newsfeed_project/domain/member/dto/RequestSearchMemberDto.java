package com.sparta.newsfeed_project.domain.member.dto;

import com.sparta.newsfeed_project.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 정보 조회 요청 DTO 클래스
 *
 * @since 2024-10-21
 */
@Getter
@NoArgsConstructor
public class RequestSearchMemberDto {
    private Long id;
    private String email;
    private String password;

    /**
     * DTO 객체를 Member 엔티티 객체로 변환
     *
     * @param memberDto 일정 생성 요청 DTO
     * @return 생성된 Member 엔티티 객체
     * @since 2024-10-21
     */
    public Member from(RequestSearchMemberDto memberDto) {
        return Member.builder()
                .id(memberDto.getId())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword()).build();
    }
}

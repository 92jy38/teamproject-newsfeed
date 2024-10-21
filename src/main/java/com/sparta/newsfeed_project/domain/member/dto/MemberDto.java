package com.sparta.newsfeed_project.domain.member.dto;

import com.sparta.newsfeed_project.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 사용자 정보를 담는 응답 DTO 클래스입니다.
 * 서버에서 클라이언트에게 유저 정보를 전달할 때 사용됩니다.
 *
 * @since 2024-10-21
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String name;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    /**
     * User 엔티티 객체를 UserDto 객체로 변환합니다.
     *
     * @param member 변환할 User 엔티티 객체
     * @return 변환된 UserDto 객체
     * @since 2024-10-18
     */
    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .name(member.getUsername())
                .createAt(member.getCreateAt())
                .updateAt(member.getUpdateAt()).build();
    }
}

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
    private String username;
    private String introduce;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    /**
     * User 엔티티 객체를 UserDto 객체로 변환합니다.
     *
     * @param member     변환할 User 엔티티 객체
     * @param hiddenInfo 숨길 정보 여부
     * @return 변환된 UserDto 객체
     * @since 2024-10-21
     */
    public static MemberDto from(Member member, boolean hiddenInfo) {
        if (hiddenInfo) {
            return MemberDto.builder()
                    .id(member.getId())
                    .email(member.getEmail())
                    .nickname(member.getNickname())
                    .username(member.getUsername()).build();
        } else {
            return MemberDto.builder()
                    .id(member.getId())
                    .email(member.getEmail())
                    .password(member.getPassword())
                    .nickname(member.getNickname())
                    .username(member.getUsername())
                    .createAt(member.getCreateAt())
                    .updateAt(member.getUpdateAt()).build();
        }
    }
}

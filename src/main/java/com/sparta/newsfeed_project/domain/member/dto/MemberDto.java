package com.sparta.newsfeed_project.domain.member.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 사용자 정보를 담는 응답 DTO 클래스입니다.
 * 서버에서 클라이언트에게 유저 정보를 전달할 때 사용됩니다.
 *
 * @since 2024-10-21
 */

@Getter
@Setter
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
    private int buddyCount;
    private int postCount;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}

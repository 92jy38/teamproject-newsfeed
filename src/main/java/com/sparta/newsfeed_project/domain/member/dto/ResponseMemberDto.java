package com.sparta.newsfeed_project.domain.member.dto;

import com.sparta.newsfeed_project.domain.common.dto.ResponseStatusDto;
import com.sparta.newsfeed_project.domain.common.exception.ResponseCode;
import com.sparta.newsfeed_project.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 서버로부터 사용자 정보를 받을 때 사용하는 DTO 클래스
 *
 * @since 2024-10-21
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMemberDto {
    private MemberDto user;
    private ResponseStatusDto status;

    /**
     * 사용자 정보와 응답 코드를 포함하는 응답 DTO를 생성합니다.
     *
     * @param member       사용자 정보 (UserDto)
     * @param hiddenInfo   숨길 정보 여부
     * @param responseCode 응답 코드
     * @return 생성된 응답 DTO 객체 (UserResponseDto)
     * @since 2024-10-21
     */
    public static ResponseMemberDto create(Member member, boolean hiddenInfo, ResponseCode responseCode) {
        return ResponseMemberDto.builder()
                .status(new ResponseStatusDto(responseCode))
                .user(MemberDto.from(member, hiddenInfo))
                .build();
    }

    /**
     * 응답 코드와 에러 메시지를 포함하는 응답 DTO를 생성합니다.
     *
     * @param responseCode 응답 코드
     * @return 생성된 응답 DTO 객체 (UserResponseDto)
     * @since 2024-10-21
     */
    public static ResponseMemberDto create(ResponseCode responseCode) {
        return ResponseMemberDto.builder()
                .status(new ResponseStatusDto(responseCode))
                .build();
    }

    /**
     * 응답 코드와 에러 메시지를 포함하는 응답 DTO를 생성합니다.
     *
     * @param responseCode 응답 코드
     * @param errorMsg     에러 메시지
     * @return 생성된 응답 DTO 객체 (UserResponseDto)
     * @since 2024-10-21
     */
    public static ResponseMemberDto create(ResponseCode responseCode, String errorMsg) {
        return ResponseMemberDto.builder()
                .status(new ResponseStatusDto(responseCode, errorMsg))
                .build();
    }
}

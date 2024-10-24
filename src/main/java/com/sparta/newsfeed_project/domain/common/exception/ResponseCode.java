package com.sparta.newsfeed_project.domain.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API 응답 시 사용되는 상태 코드와 메시지를 정의하는 enum
 *
 * @see <a href="https://ko.wikipedia.org/wiki/HTTP_%EC%83%81%ED%83%9C_%EC%BD%94%EB%93%9C">HTTP 상태 코드</a>
 * @since 2024-10-21
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {
    // 토큰 관련 에러 코드
    TOKEN_UNSIGNED(HttpStatus.BAD_REQUEST, "유효하지 않는 JWT 서명 입니다."),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, "잘못된 JWT 토큰 입니다."),
    TOKEN_TIMEOUT(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰 입니다."),
    TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "지원하지 않는 JWT 토큰 입니다."),

    // 사용자 관련 에러 코드
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    MEMBER_DELETE(HttpStatus.FORBIDDEN, "이미 탈퇴한 유저입니다."),
    MEMBER_PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    MEMBER_PASSWORD_DUPLICATED(HttpStatus.CONFLICT, "비밀번호가 중복됩니다"),
    MEMBER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이메일이 중복됩니다"),

    // 게시글 관련 에러 코드
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "이미 삭제된 게시글 입니다."),
    POST_INVALID(HttpStatus.BAD_REQUEST, "본인의 게시물이 아닙니다."),

    // 댓글 관련 에러 코드
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    COMMENT_INVALID_PERMISSION(HttpStatus.FORBIDDEN, "댓글에 대한 권한이 없습니다"),
    UNAUTHORIZED(HttpStatus.FORBIDDEN, "로그인 상태를 확인 바랍니다"),

    // TODO 보완 필요 DB & 서버 관련 에러 코드
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB 에러가 발생 하였습니다."),

    //친구 등록 관련 에러코드
    BUDDIES_DUOLICATION_ID_ERROR(HttpStatus.CONFLICT, "중복된 아이디입니다."),
    BUDDIES_DUOLICATION_ERROR(HttpStatus.CONFLICT, "존재하는 친구 목록입니다."),
    BUDDIES_NULL_ERROR(HttpStatus.NOT_FOUND, "목록에 존재하지 않습니다."),

    // 기타 에러 코드
    BAD_INPUT(HttpStatus.BAD_REQUEST, "잘못된 값 입력"),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, "허용되지 않은 파일 형식"),
    FAIL_ENCODING(HttpStatus.BAD_REQUEST, "잘못된 인코딩을 사용하였습니다."),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류");

    private final HttpStatus httpStatus;
    private final String message;
}

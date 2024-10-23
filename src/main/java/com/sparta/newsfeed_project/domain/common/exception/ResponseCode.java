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
    // 로그인 관련 코드
    SUCCESS_LOGIN(HttpStatus.OK, "로그인 성공"),
    SUCCESS_LOGOUT(HttpStatus.OK, "로그 아웃 성공"),
    INVALID_PERMISSION(HttpStatus.FORBIDDEN, "권한이 없습니다"),

    // 토큰 관련 에러 코드
    TOKEN_UNSIGNED(HttpStatus.BAD_REQUEST, "유효하지 않는 JWT 서명 입니다."),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, "잘못된 JWT 토큰 입니다."),
    TOKEN_TIMEOUT(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰 입니다."),
    TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "지원하지 않는 JWT 토큰 입니다."),

    // 사용자 관련 성공 코드
    SUCCESS_CREATE_USER(HttpStatus.CREATED, "회원 가입 성공"),
    SUCCESS_SEARCH_USER(HttpStatus.OK, "유저 조회 성공"),
    SUCCESS_UPDATE_USER(HttpStatus.OK, "유저 수정 성공"),
    SUCCESS_DELETE_USER(HttpStatus.NO_CONTENT, "유저 삭제 성공"),

    // 사용자 관련 에러 코드
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    MEMBER_DELETE(HttpStatus.NOT_FOUND, "이미 탈퇴한 유저입니다."),
    MEMBER_PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다."),
    MEMBER_PASSWORD_DUPLICATED(HttpStatus.CONFLICT, "비밀번호가 중복됩니다"),
    MEMBER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이메일이 중복됩니다"),

    // 게시글 관련 에러 코드
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "이미 삭제된 게시글 입니다."),
    POST_INVALID(HttpStatus.BAD_REQUEST, "본인의 게시물이 아닙니다."),

    // 댓글 관련 성공 코드
    SUCCESS_CREATE_COMMENT(HttpStatus.CREATED, "댓글 등록 성공"),
    SUCCESS_SEARCH_COMMENT(HttpStatus.OK, "댓글 조회 성공"),
    SUCCESS_UPDATE_COMMENT(HttpStatus.OK, "댓글 수정 성공"),
    SUCCESS_DELETE_COMMENT(HttpStatus.NO_CONTENT, "댓글 삭제 성공"),

    // 댓글 관련 에러 코드
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "이미 삭제된 일정입니다."),

    // TODO 보완 필요 DB & 서버 관련 에러 코드
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB 에러가 발생 하였습니다."),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류"),

    // 기타 에러 코드
    BAD_INPUT(HttpStatus.BAD_REQUEST, "잘못된 값 입력"),
    FAIL_ENCODING(HttpStatus.BAD_REQUEST, "잘못된 인코딩을 사용하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

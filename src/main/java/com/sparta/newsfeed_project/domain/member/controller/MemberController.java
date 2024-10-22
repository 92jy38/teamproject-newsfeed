package com.sparta.newsfeed_project.domain.member.controller;

import com.sparta.newsfeed_project.domain.common.dto.ResponseStatusDto;
import com.sparta.newsfeed_project.domain.common.exception.ResponseException;
import com.sparta.newsfeed_project.domain.member.dto.*;
import com.sparta.newsfeed_project.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 회원 관리 컨트롤러 클래스입니다.
 *
 * @since 2024-10-21
 */
@RestController
@RequiredArgsConstructor // UserService 객체를 의존성 주입 방식으로 받아오는 코드 생략 가능
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입 API
     *
     * @param requestDto 회원가입 정보 (JSON 형태)
     * @return 회원가입 처리 결과
     * @since 2023-10-21
     */
    @PostMapping("/members")
    public ResponseEntity<ResponseStatusDto> createUser(@RequestBody @Valid RequestCreateMemberDto requestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberService.createMember(requestDto));
    }

    /**
     * 로그인 API
     *
     * @param res        HttpServletResponse 객체
     * @param requestDto 로그인 정보 (JSON 형태)
     * @return 로그인 처리 결과
     * @since 2023-10-21
     */
    @PostMapping("/members/login")
    public ResponseEntity<ResponseStatusDto> login(HttpServletResponse res, @RequestBody @Valid RequestSearchMemberDto requestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.login(res, requestDto));
    }

    /**
     * 로그아웃 API
     *
     * @param res HttpServletResponse 객체
     * @return 로그인 처리 결과
     * @since 2023-10-21
     */
    @PostMapping("/members/logout")
    public ResponseEntity<ResponseStatusDto> logout(HttpServletResponse res) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.logout(res));
    }

    /**
     * 회원 정보 조회 API
     *
     * @param req      HttpServletRequest
     * @param memberId 멤버 ID
     * @return 회원 정보 조회 결과
     * @since 2023-10-21
     */
    @GetMapping("/members/{memberId}")
    public ResponseEntity<ResponseMemberDto> getUserInfo(HttpServletRequest req, @PathVariable Long memberId) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.searchMember(req, memberId));
    }

    /**
     * 회원 정보 수정 API
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 회원 정보 수정 정보 (JSON 형태)
     * @return 회원 정보 수정 결과
     * @since 2023-10-21
     */
    @PutMapping("/members")
    public ResponseEntity<ResponseStatusDto> updateUser(HttpServletRequest req, @RequestBody @Valid RequestModifyMemberDto requestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.updateMember(req, requestDto));
    }

    /**
     * 회원 정보 삭제 API
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 회원 정보 삭제 정보 (JSON 형태)
     * @return 회원 정보 삭제 결과
     * @since 2023-10-21
     */
    @DeleteMapping("/members")
    public ResponseEntity<ResponseStatusDto> deleteUser(HttpServletRequest req, @RequestBody @Valid RequestRemoveMemberDto requestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(memberService.deleteMember(req, requestDto));
    }
}

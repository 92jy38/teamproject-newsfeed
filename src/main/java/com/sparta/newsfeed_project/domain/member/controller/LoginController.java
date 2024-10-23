package com.sparta.newsfeed_project.domain.member.controller;

import com.sparta.newsfeed_project.domain.common.dto.ResponseStatusDto;
import com.sparta.newsfeed_project.domain.common.exception.ResponseException;
import com.sparta.newsfeed_project.domain.member.dto.LoginRequestDto;
import com.sparta.newsfeed_project.domain.member.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    /**
     * 로그인 API
     *
     * @param res        HttpServletResponse 객체
     * @param loginDto 로그인 정보 (JSON 형태)
     * @return 로그인 처리 결과
     * @since 2023-10-21
     */
    @PostMapping("/members/login")
    public ResponseEntity<ResponseStatusDto> login(HttpServletResponse res, @RequestBody @Valid LoginRequestDto loginDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loginService.login(res, loginDto));
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
                .body(loginService.logout(res));
    }
}

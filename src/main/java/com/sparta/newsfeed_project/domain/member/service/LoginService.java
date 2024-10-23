package com.sparta.newsfeed_project.domain.member.service;

import com.sparta.newsfeed_project.domain.common.dto.ResponseStatusDto;
import com.sparta.newsfeed_project.domain.common.exception.ResponseCode;
import com.sparta.newsfeed_project.domain.common.exception.ResponseException;
import com.sparta.newsfeed_project.domain.common.util.JwtUtil;
import com.sparta.newsfeed_project.domain.common.util.PasswordEncoder;
import com.sparta.newsfeed_project.domain.member.dto.LoginRequestDto;
import com.sparta.newsfeed_project.domain.member.entity.Member;
import com.sparta.newsfeed_project.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    /**
     * 로그인 합니다.
     *
     * @param loginDto 로그인 요청 정보
     * @return 로그인 결과 (ResponseStatusDto)
     * @since 2024-10-21
     */
    public ResponseStatusDto login(HttpServletResponse res, @Valid LoginRequestDto loginDto) throws ResponseException {
        // 로그인 요청의 비밀번호 일치 체크 후, 확인 된 멤버를 취득합니다.
        Member authMember = validateLoginInfo(loginDto.getEmail(),loginDto.getPassword());

        // 해당 멤버 id 를 subject 로 하는 전체 토큰 문자열 생성
        String token = jwtUtil.createTokenString(String.valueOf(authMember.getId()));

        // 응답 객체에 쿠키로 토큰 추가
        jwtUtil.addAsCookie(res,token);

        return new ResponseStatusDto(ResponseCode.SUCCESS_LOGIN);
    }

    /**
     * 로그아웃 합니다.
     *
     * @return 로그아웃 결과 (ResponseStatusDto)
     * @since 2024-10-21
     */
    public ResponseStatusDto logout(HttpServletResponse res) throws ResponseException {
        jwtUtil.expireCookie(res);
        return new ResponseStatusDto(ResponseCode.SUCCESS_LOGOUT);
    }

    /**
     * 로그인 시 유저 정보를 검사합니다.
     *
     * @param email    입력한 email
     * @param password 입력한 비밀번호
     * @throws ResponseException 유저 정보가 올바르지 않은 경우 예외를 발생시킵니다.
     * @since 2024-10-21
     */
    public Member validateLoginInfo(String email, String password) throws ResponseException {
        // 먼저, unique 컬럼 이메일을 활용해 멤버 엔티티를 찾습니다.
        Member findMember = memberRepository.findByEmail(email);

        // 멤버가 없는 경우, 해당 예외를 반환합니다.
        if (findMember == null) {
            throw new ResponseException(ResponseCode.MEMBER_NOT_FOUND);
        }
        // 이어서, 비밀번호를 유틸을 통해 matches 메서드에 적용해봅니다. 결과가 false 일 경우, 해당 예외를 반환합니다.
        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new ResponseException(ResponseCode.MEMBER_PASSWORD_NOT_MATCH);
        }
        // 마지막으로, 찾은 멤버가 혹시나 삭제된 멤버일 경우, 해당 예외를 반환합니다.
        if (findMember.isDeleted()) {
            throw new ResponseException(ResponseCode.MEMBER_DELETE);
        }

        // 두 과정이 문제 없다면, 인증된 멤버 엔티티를 반환합니다.
        return findMember;
    }
}

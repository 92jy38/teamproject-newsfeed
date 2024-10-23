package com.sparta.newsfeed_project.domain.common.filter;

import com.sparta.newsfeed_project.domain.common.exception.ResponseCode;
import com.sparta.newsfeed_project.domain.common.exception.ResponseException;
import com.sparta.newsfeed_project.domain.common.util.JwtUtil;
import com.sparta.newsfeed_project.domain.member.entity.Member;
import com.sparta.newsfeed_project.domain.member.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@RequiredArgsConstructor
public class AuthFilter extends HttpFilter {
    private final JwtUtil jwtUtil;

    // 필터 성공 시, 요청 객체에 포함되는 사용자 정보 (id) 의 속성 키값
    private final String SUBJECT_ATTRIBUTE_KEY = "loggedInWithId";

    // 로그인 절차를 생략할 API 경로 추가
    private final String[] JWT_BYPASS_PATHS = {
            "/api/members/login","/api/members/login", "/api/members/signup"
    };
    private final MemberRepository memberRepository;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String url = request.getRequestURI();

        if (!(StringUtils.hasText(url) && (urlMatches(url, JWT_BYPASS_PATHS)))) {
            String token = jwtUtil.getTokenFromRequest(request);
            if (StringUtils.hasText(token)) {
                String postJwt = jwtUtil.removeBearerPrefix(token);
                String idFromSubject = jwtUtil.getSubject(postJwt);
                Member memberFromSubject = memberRepository.findById(Long.valueOf(idFromSubject)).orElseThrow(() -> new ResponseException(ResponseCode.MEMBER_NOT_FOUND));
                request.setAttribute(SUBJECT_ATTRIBUTE_KEY, memberFromSubject);
            }
        }

        chain.doFilter(request, response);
    }

    public boolean urlMatches(String url, String[] paths) {
        for (String path : paths) {
            if (url.startsWith(path)) {
                return true;
            }
        }
        return false;
    }
}

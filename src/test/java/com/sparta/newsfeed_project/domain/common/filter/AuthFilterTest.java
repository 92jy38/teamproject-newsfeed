package com.sparta.newsfeed_project.domain.common.filter;

import com.sparta.newsfeed_project.domain.common.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;

class AuthFilterTest {
    private AuthFilter authFilter;
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        this.jwtUtil = new JwtUtil();
        jwtUtil.testInit();
//        this.authFilter = new AuthFilter(jwtUtil);
    }

    @Test
    @DisplayName("matches 테스트")
    void test() {
        String[] patterns = {"/a","/b","/c/d"};

        Assertions.assertTrue(authFilter.urlMatches("/a",patterns));
        Assertions.assertTrue(authFilter.urlMatches("/a/c",patterns));
        Assertions.assertTrue(authFilter.urlMatches("/b",patterns));
        Assertions.assertTrue(authFilter.urlMatches("/b/c",patterns));
        Assertions.assertTrue(authFilter.urlMatches("/c/d",patterns));

        Assertions.assertFalse(authFilter.urlMatches("a",patterns));
        Assertions.assertFalse(authFilter.urlMatches("/c",patterns));
    }

    // No Assertions.w
    @Test
    @DisplayName("filter 테스트")
    void doFilter() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        String serializedValue = URLEncoder.encode("test_id_subject", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        Cookie cookie = new Cookie("Authorization", jwtUtil.createTokenString(serializedValue));
        cookie.setPath("/");

        Cookie[] cookies = new Cookie[] {cookie};
        when(request.getCookies()).thenReturn(cookies);
        doAnswer(invocationOnMock -> {
            String name = invocationOnMock.getArgument(0);
            String value = invocationOnMock.getArgument(1);
            System.out.println(name + ":" + value);
            return null;
        }).when(request).setAttribute(anyString(),any());

        authFilter.doFilter(request,response,mock(FilterChain.class));
    }
}
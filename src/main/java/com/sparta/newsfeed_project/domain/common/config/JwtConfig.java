package com.sparta.newsfeed_project.domain.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sparta.newsfeed_project.domain.common.util.JwtUtil;

/**
 * JWT 토큰 유틸 설정을 담당하는 클래스입니다.
 *
 * @since 2024-10-22
 */
@Configuration
public class JwtConfig {

    /**
     * JWT 토큰 유틸 객체를 생성하여 Bean 으로 등록합니다.
     *
     * @return Bean 으로 등록된 JwtUtil 객체
     */
    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}

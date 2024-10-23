
package com.sparta.newsfeed_project.domain.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sparta.newsfeed_project.domain.common.util.PasswordEncoder;

/**
 * 비밀번호 암호화 설정을 담당하는 클래스입니다.
 *
 * @since 2024-10-17
 */
@Configuration
public class PasswordConfig {

    /**
     * 비밀번호 암호화 객체를 생성하여 Bean 으로 등록합니다.
     *
     * @return Bean 으로 등록된 PasswordEncoder 객체
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder();
    }

}


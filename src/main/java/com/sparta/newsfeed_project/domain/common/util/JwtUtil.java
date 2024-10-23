package com.sparta.newsfeed_project.domain.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    // 환경 변수로 지정된 base64 비밀 키 문자열을 불러올 위치
    @Value("${jwt.secret.key}")
    private String stringKey;

    // SecretKey 객체를 담을 전역 필드
    private SecretKey secretKey;

    // 생성 시점에 동적으로 불러온 비밀 키 문자열을, HMAC-SHA 메서드로 SecretKey 타입으로 필드에 저장하는 프로시저
    @PostConstruct
    public void init() {
        byte[] keyBytes = stringKey.getBytes();
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public void testInit() {
        stringKey = "ZXhhbXBsZSB0ZXN0IHNlY3JldCBrZXk=";
        byte[] keyBytes = stringKey.getBytes();
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // subject 문자열을 전달하면, 갖춰진 정보를 토대로 전체 JWT 토큰 문자열을 반환해주는 메서드
    public String createTokenString(String subject) {
        Date issuedAt = new Date();
        long TOKEN_EXPIRATION_TIME = 60 * 60 * 1000L;
        Date expiration = new Date(issuedAt.getTime() + TOKEN_EXPIRATION_TIME);

        String postJwt = Jwts.builder()
                .setSubject(subject)
                .signWith(secretKey)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .compact();

        return BEARER_PREFIX + postJwt;
    }

    // 전달된 response 객체 내부에 직렬화된 값을 지정된 헤더에 쿠키 형태로 추가하는 메서드
    public void addAsCookie(HttpServletResponse response, String value) {
        try {
            String serializedValue = URLEncoder.encode(value, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, serializedValue);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (RuntimeException ex) {
            throw new RuntimeException("인가 토큰 발행 중 오류.("+ex.getMessage()+")",ex.getCause());
        }
    }

    // 전달된 response 객체에 예상되는 기존 쿠키를 만료된 쿠키로 덮어씌우는 메서드
    public void expireCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    // 토큰 문자열의 접두어 "Bearer" 만을 제거하는 메서드
    public String removeBearerPrefix(String withPrefix) {
        if (StringUtils.hasText(withPrefix) && withPrefix.startsWith(BEARER_PREFIX)) {
            return withPrefix.substring(BEARER_PREFIX.length());
        } else {
            throw new RuntimeException("토큰 형식이 올바르지 않습니다.");
        }
    }

    // 토큰 문자열의 유효한 파싱 결과를 반환하는 메서드
    private Jws<Claims> validateToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        } catch (RuntimeException ex) {
            throw new RuntimeException("토큰이 유효하지 않습니다.", ex.getCause());
        }
    }

    // 파싱 가능한 형태로 추출된 토큰 문자열의 유효성을 확인한 뒤 subject 를 반환하는 메서드
    public String getSubject(String token) {
        Jws<Claims> validated = validateToken(token);
        Claims claims = validated.getBody();
        return claims.getSubject();
    }

    // 전달된 request 객체의 쿠키 중 인가에 관한(JWT 가 포함된) 쿠키의 값을 역직렬화하여 반환하는 메서드
    public String getTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (!Objects.isNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                    } catch (RuntimeException ex) {
                        throw new RuntimeException("토큰 값을 해석할 수 없습니다.", ex);
                    }
                }
            }
        }
        throw new RuntimeException("토큰이 없거나 만료되었습니다.");
    }
}

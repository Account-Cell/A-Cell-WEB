package com.lastdance.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    // 비밀키 설정
    private final String SECRET_KEY = "58544cd06ab9e13ef4bd7438fbdfa33f1401a8567c30535f3f6d6969d45ef31b5680e2999f0722978d8de79a9334a0a8af0c26eea6acf8e5aa4db2cdb17a668e";

    // 토큰 만료 시간 (예: 1시간)
    private final long EXPIRATION_TIME = 3600000; // 1시간(밀리초)

    // JWT 토큰 생성
    public String createToken(String userId) {
        return JWT.create()
                .withSubject(userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    // JWT 토큰 검증
    public boolean verifyToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            // 검증 실패 시 예외 처리
            return false;
        }
    }

    // JWT에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        return JWT.decode(token).getSubject();
    }
}

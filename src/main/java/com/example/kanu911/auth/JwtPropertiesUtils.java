package com.example.kanu911.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtPropertiesUtils {
    public static final String SECRET = "test12#$";
    public static final int EXPIRATION_TIME = 604800000; // 7 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static DecodedJWT verify(String token) {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(JwtPropertiesUtils.SECRET.getBytes() ))
                .acceptExpiresAt(JwtPropertiesUtils.EXPIRATION_TIME)  // 7day
                .build();

        return verifier.verify(token);
    }

    public static String create(String userId, String userName, String role) {
        String token = JWT.create().withSubject(userId).withClaim("role", role)
                .withClaim("userId", userId)
                .withClaim("userName", userName)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtPropertiesUtils.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(JwtPropertiesUtils.SECRET.getBytes()));
        return token;
    }
}

package com.mosan.mosan.rest.jwtoken.utils;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface IJwtTokenProvider {
    String generateToken(Authentication authentication);
    Claims parseToken(String token);
    String generateTokenFromString(String userId);
}

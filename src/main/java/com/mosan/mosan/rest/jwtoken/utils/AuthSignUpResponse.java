package com.mosan.mosan.rest.jwtoken.utils;
import java.util.UUID;
public record AuthSignUpResponse(UUID id, String username, String email, String access_token, String refresh_token) {
}

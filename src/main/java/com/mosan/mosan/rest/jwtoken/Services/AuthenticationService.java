package com.mosan.mosan.rest.jwtoken.Services;

import com.mosan.mosan.rest.jwtoken.utils.IJwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private IJwtTokenProvider _jwtTokenProvider;
    @Autowired
    private AuthenticationManager _authenticationManager;
    @Autowired
    private PasswordEncoder _passwordEncoder;
}

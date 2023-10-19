package com.mosan.mosan.rest.jwtoken.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosan.mosan.rest.jwtoken.utils.AuthSignInResponse;
import com.mosan.mosan.rest.jwtoken.utils.AuthSignUpResponse;
import com.mosan.mosan.rest.jwtoken.utils.IJwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class JwtUsernameAndPasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public IJwtTokenProvider _jwtTokenProvider;
    public JwtUsernameAndPasswordAuthenticationFilter(String defaultFilterProcessesUrl, IJwtTokenProvider jwtTokenProvider) {
        super(defaultFilterProcessesUrl);
        _jwtTokenProvider=jwtTokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        System.out.println( request.getRequestURI());

        ObjectMapper mapper=new ObjectMapper();
        UserCredentials credentials=mapper.readValue(request.getInputStream(),UserCredentials.class);
        System.out.println(credentials.getUsername());
        System.out.println(credentials.getPassword());


        try{

            UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(credentials.getUsername(),credentials.getPassword());
            return getAuthenticationManager().authenticate(authentication);

        }catch(Exception e){
            log.debug(e.getMessage(),e);
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
       String token=_jwtTokenProvider.generateToken(authResult);
       ObjectMapper mapper=new ObjectMapper();
       OutputStream responseOutputSTream=response.getOutputStream();
        AuthSignInResponse authResponse=new AuthSignInResponse("Bearer "+ token,"token");
       response.setContentType(MediaType.APPLICATION_JSON_VALUE);
       mapper.writeValue(responseOutputSTream,authResponse);
       responseOutputSTream.flush();

    }

}

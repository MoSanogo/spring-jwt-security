package com.mosan.mosan.rest.jwtoken.utils;


import com.mosan.mosan.rest.jwtoken.constants.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
@Setter @Getter
@Component
public class JwtTokenProvider implements IJwtTokenProvider {

    @Value("${secret}")
    public String jwtSecret;
    @Value("${expirationInMs}")
    public int jwtExpirationInMs;
//    private SecretKey key=Jwts.SIG.HS512.key().build();

  @Override
  public String generateToken(Authentication authentication){

      org.springframework
              .security.core
              .userdetails
              .User userPrincipal = (org.springframework
              .security.core.userdetails
              .User)authentication.getPrincipal();
      Date now=new Date();
      Date expirationDate=new Date(now.getTime() + jwtExpirationInMs);
//      SecretKey key= new SecretKeySpec(Base64.getDecoder().decode(jwtSecret),
//              MacAlgorithm.);

      return Jwts.builder()
              .issuer(AppConstants.ISSUER)
              .subject(userPrincipal.getUsername())
              .expiration(expirationDate)
              .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
              .compact();

  }

    @Override
    public Claims parseToken(String token){
       try {

        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();


       }catch(Exception e){
           throw new RuntimeException(e.getMessage());
       }
    }
    @Override
    public String generateTokenFromString(String username){
        Date now=new Date();
        Date expirationDate=new Date(now.getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .issuer(AppConstants.ISSUER)
                .subject(username)
                .expiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
}

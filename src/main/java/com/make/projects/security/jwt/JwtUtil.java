package com.make.projects.security.jwt;
import com.make.projects.config.auth.CustomUserDetails;

import com.make.projects.exception.authexception.JwtTokenException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtUtil {

    private static final String AUTHORITIES_KEY = "roles";

    @Value("${application.security.jwt.secret}")
    private String secretKey;

    @Value("${application.security.jwt.token-validity-in-seconds}")
    private long tokenValidityInMilliseconds;

    @Value("${application.security.jwt.token-validity-in-seconds-for-remember-me}")
    private long tokenValidityInMillisecondsForRememberMe;

    @Value("${application.security.jwt.refresh-token-validity-in-seconds}")
    private long refreshValidityInMilliseconds;
/*    public JwtUtil(ApplicationProperties applicationProperties) {
        this.secretKey = applicationProperties.getSecurity().getJwt().getSecret();
        this.tokenValidityInMilliseconds = 1000 * applicationProperties.getSecurity().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe = 1000 * applicationProperties.getSecurity().getJwt().getTokenValidityInSecondsForRememberMe();
    }*/


    public String createToken(Authentication authentication) {
        return createToken(authentication, false);
    }

    public String createToken(Authentication authentication, Boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;

        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .setIssuedAt(new Date())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("id", customUserDetails.getUsername())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(validity)
                .compact();

    }

    /**
     * ???????????? ????????? ?????? ????????? ????????? jwt??? ????????????, ??????????????? ???????????? ??????
     * ??? ???????????? ???????????? ???????????? ?????? ??????????????? X
     */

    public String getUserIdFromToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    //Token ????????? ??????
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.info("???????????? ?????? ??????");
            throw new JwtTokenException(e.getMessage(), e.getCause(), HttpStatus.FORBIDDEN);
        } catch (MalformedJwtException e) {
            log.info("?????? ?????? ??????");
            throw new JwtTokenException(e.getMessage(), e.getCause(), HttpStatus.FORBIDDEN);
        } catch (ExpiredJwtException e) {
            log.info("?????? ?????? ??????");
            throw new JwtTokenException(e.getMessage(), e.getCause(), HttpStatus.FORBIDDEN);
        } catch (UnsupportedJwtException e) {
            log.info("????????? ????????? ????????????");
            throw new JwtTokenException(e.getMessage(), e.getCause(), HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            log.info("???????????? ??????");
            throw new JwtTokenException(e.getMessage(), e.getCause(), HttpStatus.FORBIDDEN);
        }

    }

}

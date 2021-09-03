package com.make.projects.security.jwt;
import com.make.projects.config.ApplicationProperties;
import com.make.projects.service.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtUtil {

    private static final String AUTHORITIES_KEY ="roles";

    private String secretKey;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    private ApplicationProperties applicationProperties;

    public JwtUtil(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        this.secretKey = applicationProperties.getSecurity().getJwt().getSecret();
        this.tokenValidityInMilliseconds = 1000 * applicationProperties.getSecurity().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe = 1000 * applicationProperties.getSecurity().getJwt().getTokenValidityInSecondsForRememberMe();
    }

    public String createToken(Authentication authentication) {
        return createToken(authentication,false);
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
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(validity)
                .compact();

    }

    /**
     *  비밀키를 이용해 현재 복호화 하려는 jwt가 유효한지, 위변조되지 않았는지 판단
     *  이 비밀키는 서버에만 존재해야 하며 유출되어선 X
     */

    public String getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e){
            log.info("유효하지 않은 서명");
        } catch (MalformedJwtException e){
            log.info("토큰 구성 오류");
        } catch (ExpiredJwtException e){
            log.info("만료 기간 초과");
        } catch (UnsupportedJwtException e){
            log.info("기존의 형식과 다릅니다");
        } catch (IllegalArgumentException e){
            log.info("부적합한 인자");
        }

        return false;
    }

}
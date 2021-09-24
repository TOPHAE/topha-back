package com.make.projects.config;

import com.make.projects.exception.authexception.Http401ErrorEntryPoint;
import com.make.projects.security.jwt.JwtAuthFilter;
import com.make.projects.security.oauth2.CustomOAuth2UserService;
import com.make.projects.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.make.projects.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.make.projects.security.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*https://github.com/keumtae-kim/spring-boot-react-blog*/
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private CustomOAuth2UserService customOauth2UserService;
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Autowired
    public SecurityConfig(CustomOAuth2UserService customOauth2UserService, OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler, OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler){
        this.customOauth2UserService = customOauth2UserService;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
    }

    public void configure(HttpSecurity http) throws Exception {
        http.
                cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//세션정책을 설정한다는것은 인증 처리 관점에서 스프링 시큐리티가 더 이상 세션쿠키 방식의 인증 메카니즘으로 인증처리를 하지 않겠다는 의미
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new Http401ErrorEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
                //.antMatchers(HttpMethod.POST, "/api/project/**").access("hasRole('ROLE_USER')")
                //.antMatchers(HttpMethod.POST,"/api/comment/**").access("hasRole('ROLE_USER')")
                .antMatchers("/api/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
            .and()
                .oauth2Login()
                .authorizationEndpoint()//인가에 대한 요청을 서비스할 때 사용한다. 기본 URL은 /oauth/authorize
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(cookieAuthorizationRequestRepository()) // 인가 요청을 시작한 시점부터 인가 요청을 받는 시점까지 (콜백) OAuth2AuthorizationRequest를 유지해준다.
            .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
            .and()
                .userInfoEndpoint()
                .userService(customOauth2UserService)
            .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);

    }
    @Bean
    public Http401ErrorEntryPoint http401ErrorEntryPoint() {
        return new Http401ErrorEntryPoint();
    }

    @Bean
    public JwtAuthFilter tokenAuthenticationFilter() {
        return new JwtAuthFilter();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private AuthorizationRequestRepository<OAuth2AuthorizationRequest> cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }
}


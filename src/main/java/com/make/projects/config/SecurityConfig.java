package com.make.projects.config;

import com.make.projects.security.oauth2.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOauth2UserService customOauth2UserService;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//세션정책을 설정한다는것은 인증 처리 관점에서 스프링 시큐리티가 더 이상 세션쿠키 방식의 인증 메카니즘으로 인증처리를 하지 않겠다는 의미
        http.formLogin().disable();
        http.httpBasic().disable();
/* .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")*/

        http.authorizeRequests()
                .antMatchers("/api/**").access("hasRole('ROLE_USER')")
                .antMatchers("/api/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
            .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOauth2UserService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

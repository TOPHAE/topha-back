package com.make.projects.controller;

import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.model.domain.Users;
import com.make.projects.model.dto.Result;
import com.make.projects.model.dto.lookup.ResponseOAuthUser;
import com.make.projects.repository.datajpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {

    private final UserRepository userRepository;

    @GetMapping("/auth/userInfo")
    public Result<ResponseOAuthUser> signup(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Users user = userDetails.getUser();
        ResponseOAuthUser oauthUserInfo = ResponseOAuthUser.builder()
                .provider(user.getProvider().name())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .roles(user.getRoles())
                .userId(user.getUserId())
                .build();
        return new Result<>(oauthUserInfo,HttpStatus.OK.value());
    }
    

    @GetMapping("/user/{id}")
    public Result<Users> selectUser(@PathVariable Long id) throws Exception {
        Users users = userRepository.findById(id).orElseThrow(Exception::new);
        return new Result<>(users, HttpStatus.OK.value());
    }

}

package com.make.projects.controller;

import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.model.domain.Users;
import com.make.projects.model.dto.RequestUpdateUser;
import com.make.projects.model.dto.Result;
import com.make.projects.model.dto.lookup.ResponseOAuthUser;
import com.make.projects.repository.datajpa.UserRepository;
import com.make.projects.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/auth/userInfo")
    public Result<ResponseOAuthUser> signup(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("유저 로그인={}", userDetails.getUser());
        Users user = userDetails.getUser();
        ResponseOAuthUser oauthUserInfo = ResponseOAuthUser.builder()
                .provider(user.getProvider().name())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .roles(user.getRoles())
                .userId(user.getUserId())
                .build();

        return new Result<>(oauthUserInfo, HttpStatus.OK.value());
    }

    @GetMapping("/checkNewUser")
    public Result<Boolean> signupNicknameCheck(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("유저 닉네임 확인={}", userDetails.getUser().getNickname());

        if (userDetails.getUser().getNickname() == null) {
            return new Result<>(true, HttpStatus.OK.value());
        } else {
            return new Result<>(false, HttpStatus.OK.value());
        }
    }

    @PostMapping("/updateUser")
    public Result<ResponseOAuthUser> updateUser(@RequestBody RequestUpdateUser requestUpdateUser, @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("업데이트 필드값={}", requestUpdateUser);
        ResponseOAuthUser responseOAuthUser = userService.updateUser(requestUpdateUser, userDetails);
        
        return new Result<>(responseOAuthUser, HttpStatus.OK.value());
    }


    

    @GetMapping("/user/{id}")
    public Result<Users> selectUser(@PathVariable Long id) throws Exception {
        Users users = userRepository.findById(id).orElseThrow(Exception::new);
        return new Result<>(users, HttpStatus.OK.value());
    }


}

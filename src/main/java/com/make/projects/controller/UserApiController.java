package com.make.projects.controller;

import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.model.domain.Users;
import com.make.projects.model.dto.RequestSaveUser;
import com.make.projects.model.dto.Result;
import com.make.projects.model.dto.lookup.ResponseUserDto;
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
    public Result<ResponseUserDto> signup(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("유저 로그인={}", userDetails.getUser());
        Users user = userDetails.getUser();
        ResponseUserDto oauthUserInfo = ResponseUserDto.builder()
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
            return new Result<>(true, HttpStatus.NOT_FOUND.value());
        } else {
            return new Result<>(false, HttpStatus.OK.value());
        }

    }

    @PostMapping("/updateUser")
    public Result<ResponseUserDto> updateUser(@RequestBody RequestSaveUser requestSaveUser, @AuthenticationPrincipal CustomUserDetails userDetails) {
        ResponseUserDto responseUserDto = userService.updateUser(requestSaveUser, userDetails);
        
        return new Result<>(responseUserDto, HttpStatus.OK.value());
    }

}

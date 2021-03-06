package com.make.projects.controller;

import com.make.projects.annotaions.LoginUser;
import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.model.domain.User;
import com.make.projects.model.dto.RequestSaveUser;
import com.make.projects.model.dto.Result;
import com.make.projects.model.dto.lookup.ResponseUserDto;
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

    private final UserService userService;

    @PostMapping("/auth/userInfo")
    public Result<?> signup(@AuthenticationPrincipal CustomUserDetails userDetails,@LoginUser User loginUser) {

        ResponseUserDto responseUserDto = userService.saveUser(userDetails.getUser());
        return new Result<>(responseUserDto, HttpStatus.OK.value());
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

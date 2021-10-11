package com.make.projects.controller;

import com.make.projects.annotaions.LoginUser;
import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.model.domain.Users;
import com.make.projects.repository.datajpa.user.UserRepository;
import com.make.projects.service.LikeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class LikeApiController {

    private final LikeService likeService;

    @ApiOperation(value = "좋아요 카운트")
    @GetMapping("/like/{projectId}")
    public ResponseEntity<List<String>> getLikeCount(@PathVariable Long projectId,
                                                     @LoginUser Users loginUser) throws Exception {
        log.info("project-id : {}", projectId);
        log.info("loginUser : {} ", loginUser);

        List<String> resultData = likeService.count(projectId,loginUser);

        log.info("likeCount : {} " , resultData);

        return new ResponseEntity<>(resultData , HttpStatus.OK);
    }

    @ApiOperation(value = "좋아요 취소")
    @DeleteMapping("/like/{projectId}")
    public ResponseEntity<String> cancelLike(@LoginUser Users loginUser,
                                             @PathVariable Long projectId){
        if(loginUser != null){
            likeService.cancelLike(loginUser, projectId);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "좋아요 등록")
    @PostMapping("/like/{projectId}")
    public ResponseEntity<String> addLike(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long projectId) {
        boolean result = false;

        if (customUserDetails.getUsername() != null) {
            result = likeService.addLike(customUserDetails.getUser(), projectId);
        }

        return result ?
                new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

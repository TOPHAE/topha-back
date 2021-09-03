package com.make.projects.controller;

import com.make.projects.model.domain.Users;
import com.make.projects.model.dto.ResponseDto;
import com.make.projects.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {

    private final UserRepository userRepository;

    @GetMapping("/user/{id}")
    public ResponseDto<Users> selectUser(@PathVariable Long id) throws Exception {
        Users users = userRepository.findById(id).orElseThrow(Exception::new);
        return new ResponseDto<>(users, HttpStatus.OK.value());
    }

}

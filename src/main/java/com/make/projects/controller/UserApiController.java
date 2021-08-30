package com.make.projects.controller;

import com.make.projects.entity.Users;

import com.make.projects.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody Users user){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), 1234));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity(HttpStatus.OK);
    }
}

package com.make.projects.service;

import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.model.domain.Users;
import com.make.projects.model.dto.RequestSaveUser;
import com.make.projects.model.dto.lookup.ResponseUserDto;
import com.make.projects.repository.datajpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public ResponseUserDto updateUser(RequestSaveUser requestSaveUser, CustomUserDetails customUserDetails){

        Users users = userRepository.findById(customUserDetails.getUser().getUserId()).get();
        users.setNickname(requestSaveUser.getNickname());
        users.setSpecialty(requestSaveUser.getSpecialty());


        return ResponseUserDto.builder()
                .userId(users.getUserId())
                .roles(users.getRoles())
                .email(users.getEmail())
                .provider(users.getProvider().name())
                .nickname(users.getNickname())
                .specialty(users.getSpecialty())
                .build();

    }

    public ResponseUserDto saveUser(Users user) {

        return ResponseUserDto.builder()
                .specialty(user.getSpecialty())
                .provider(user.getProvider().name())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .roles(user.getRoles())
                .userId(user.getUserId())
                .imgUrl(user.getImgUrl())
                .build();

    }
}

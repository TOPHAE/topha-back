package com.make.projects.service;

import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.model.domain.User;
import com.make.projects.model.dto.RequestSaveUser;
import com.make.projects.model.dto.lookup.ResponseUserDto;
import com.make.projects.repository.datajpa.user.UserRepository;
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

        User User = userRepository.findById(customUserDetails.getUser().getUserId()).get();
        User.setNickname(requestSaveUser.getNickname());
        User.setSpecialty(requestSaveUser.getSpecialty());


        return ResponseUserDto.builder()
                .userId(User.getUserId())
                .roles(User.getRoles())
                .email(User.getEmail())
                .provider(User.getProvider().name())
                .nickname(User.getNickname())
                .specialty(User.getSpecialty())
                .build();

    }

    public ResponseUserDto saveUser(User user) {
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

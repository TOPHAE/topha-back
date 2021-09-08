package com.make.projects.service;

import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.model.domain.Users;
import com.make.projects.model.dto.RequestUpdateUser;
import com.make.projects.model.dto.lookup.ResponseOAuthUser;
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
    public ResponseOAuthUser updateUser(RequestUpdateUser requestUpdateUser, CustomUserDetails customUserDetails){

        Users users = userRepository.findById(customUserDetails.getUser().getUserId()).get();
        users.setNickname(requestUpdateUser.getNickname());
        users.setSpecialty(requestUpdateUser.getSpecialty());


        return ResponseOAuthUser.builder()
                .userId(users.getUserId())
                .roles(users.getRoles())
                .email(users.getEmail())
                .provider(users.getProvider().name())
                .nickname(users.getNickname())
                .specialty(users.getSpecialty())
                .build();

    }

}

package com.make.projects.service;

import com.make.projects.config.auth.CustomUserDetails;
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

    public ResponseOAuthUser updateUser(RequestUpdateUser requestUpdateUser, CustomUserDetails customUserDetails){
        customUserDetails.getUser().setSpecialty(requestUpdateUser.getTech());
        customUserDetails.getUser().setNickname(requestUpdateUser.getNickname());

        return ResponseOAuthUser.builder()
                .userId(customUserDetails.getUser().getUserId())
                .roles(customUserDetails.getUser().getRoles())
                .email(customUserDetails.getUser().getEmail())
                .provider(customUserDetails.getUser().getProvider().name())
                .nickname(customUserDetails.getUser().getNickname())
                .userTech(customUserDetails.getUser().getSpecialty())
                .build();

    }

}

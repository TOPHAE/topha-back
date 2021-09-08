package com.make.projects.service;

import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.model.dto.lookup.RequestOAuthUser;
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

    public ResponseOAuthUser updateUser(RequestOAuthUser requestOAuthUser, CustomUserDetails customUserDetails){
        customUserDetails.getUser().setSpecialty(requestOAuthUser.getTech());
        customUserDetails.getUser().setNickname(requestOAuthUser.getNickname());

        return ResponseOAuthUser.builder()
                .userId(customUserDetails.getUser().getUserId())
                .roles(customUserDetails.getUser().getRoles())
                .email(customUserDetails.getUser().getEmail())
                .nickname(customUserDetails.getUser().getNickname())
                .provider(customUserDetails.getUser().getProvider().name())
                .build();

    }

}

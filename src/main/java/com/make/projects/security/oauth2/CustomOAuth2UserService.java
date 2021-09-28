package com.make.projects.security.oauth2;

import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.exception.authexception.InternalAuthenticationServiceException;
import com.make.projects.exception.authexception.OAuth2AuthenticationEx;
import com.make.projects.model.domain.Users;
import com.make.projects.model.domain.enumType.Provider;
import com.make.projects.repository.datajpa.user.UserRepository;
import com.make.projects.security.oauth2.user.OAuth2UserInfo;
import com.make.projects.security.oauth2.user.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * DefaultOAuth2UserService 란?
 * OAuth2 공급자로부터 액세스 토큰을 얻은 후에 호출됩니다.
 * 이 방법에서는 먼저 OAuth2 제공 업체에서 사용자의 세부 정보를 가져옵니다.
 * 동일한 이메일을 사용하는 사용자가 이미 데이터베이스에 있으면 세부 정보를 업데이트하고, 그렇지 않으면 새 사용자를 등록합니다.
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        System.out.println(oAuth2User.getAttributes());

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationEx("not found Email", HttpStatus.NOT_FOUND);
        }

        Optional<Users> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        Users user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationEx("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.", HttpStatus.NOT_FOUND);
            }
            //user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return new CustomUserDetails(user, oAuth2User.getAttributes());
    }

    private Users registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {

        Users userInfo = Users.builder()
                .provider(Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .email(oAuth2UserInfo.getEmail())
                .imgUrl(oAuth2UserInfo.getImageUrl())
                .roles("ROLE_USER")
                .build();


        return userRepository.save(userInfo);
    }

    private Users updateExistingUser(Users existingUser, OAuth2UserInfo oAuth2UserInfo) {
        //existingUser.setNickname(oAuth2UserInfo.getName());
        existingUser.setImgUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }
}

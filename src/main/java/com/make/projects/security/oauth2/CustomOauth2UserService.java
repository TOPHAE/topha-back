package com.make.projects.security.oauth2;

import com.make.projects.entity.Users;
import com.make.projects.entity.enumType.Provider;
import com.make.projects.entity.enumType.Role;
import com.make.projects.exception.OAuth2AuthenticationEx;
import com.make.projects.repository.UserRepository;
import com.make.projects.security.UserPrincipal;
import com.make.projects.security.oauth2.user.OAuth2UserInfo;
import com.make.projects.security.oauth2.user.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        System.out.println("카카오"+ oAuth2UserRequest);
        System.out.println("카카오 = " + oAuth2User.getAttributes());
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        }catch (OAuth2AuthenticationException ex){
            throw ex;
        }catch (Exception ex){
            throw new OAuth2AuthenticationEx(ex.getMessage());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())){
            throw new OAuth2AuthenticationEx("이메일을 찾을 수 없습니다.");
        }

        Optional<Users> userInfo = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        Users user;
        if(userInfo.isPresent()){
            user = userInfo.get();
            if(!user.getProvider().equals(Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))){
                throw new OAuth2AuthenticationEx("이미 회원가입한 아이디 입니다");
            }
            user = updateExistingUser(user,oAuth2UserInfo);
        }else {
            user = registerNewUser(oAuth2UserRequest,oAuth2UserInfo);
        }


        return new UserPrincipal(user,oAuth2User.getAttributes());
    }

    private Users registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo){
        System.out.println("요고봐라= " + Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        Users newUserInfo = Users
                .builder()
                .provider(Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .nickName(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .imgUrl(oAuth2UserInfo.getImageUrl())
                .roles(Role.ROLE_USER)
                .build();
        return userRepository.save(newUserInfo);


    }

    private Users updateExistingUser(Users existingUser, OAuth2UserInfo oAuth2UserInfo){

        Users updateUserInfo = existingUser
                .builder()
                .nickName(oAuth2UserInfo.getName())
                .imgUrl(oAuth2UserInfo.getImageUrl())
                .build();

        return userRepository.save(updateUserInfo);
    }
}

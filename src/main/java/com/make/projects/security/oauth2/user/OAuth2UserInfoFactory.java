package com.make.projects.security.oauth2.user;

import com.make.projects.model.domain.enumType.Provider;
import com.make.projects.exception.authexception.OAuth2AuthenticationEx;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String,Object> attributes){

        if(registrationId.equalsIgnoreCase(Provider.google.name())){
            return new GoogleOAuth2UserInfo(attributes);
        }else if(registrationId.equalsIgnoreCase(Provider.github.name())) {
            return new GithubOAuth2UserInfo(attributes);
        }else if(registrationId.equalsIgnoreCase(Provider.kakao.name())){
            Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
            return new KakaoOAuth2UserInfo(kakao_account);
        }else{
            throw new OAuth2AuthenticationEx("죄송합니다 아직 지원하지않는 소셜입니다.", HttpStatus.BAD_REQUEST);
        }
    }
}

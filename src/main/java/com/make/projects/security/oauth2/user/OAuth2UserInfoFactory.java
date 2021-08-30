package com.make.projects.security.oauth2.user;

import com.make.projects.entity.enumType.Provider;
import com.make.projects.exception.OAuth2AuthenticationEx;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String,Object> attributes){

        if(registrationId.equalsIgnoreCase(Provider.google.name())){
            return new GoogleOAuth2UserInfo(attributes);
        }else if(registrationId.equalsIgnoreCase(Provider.facebook.name())) {
            return new FacebookOAuth2UserInfo(attributes);
        }else if(registrationId.equalsIgnoreCase(Provider.kakao.name())){
            Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
            return new KakaoOAuth2UserInfo(kakao_account);
        }else{
            throw new OAuth2AuthenticationEx("죄송합니다" + registrationId + "는지원하지 않습니다.");
        }
    }
}

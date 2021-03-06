package com.make.projects.config.auth;

import com.make.projects.model.domain.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


@Getter
public class CustomUserDetails implements OAuth2User, UserDetails {

    private Users user;
    private Map<String,Object> attributes; //auth정보받을곳


    public CustomUserDetails(Users user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public CustomUserDetails(Users user) {
        this.user=user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRoles();
            }
        });
        return collection;
    }

    @Override
    public String getName() {
        return user.getNickname();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}

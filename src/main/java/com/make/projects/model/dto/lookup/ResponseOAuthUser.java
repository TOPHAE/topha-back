package com.make.projects.model.dto.lookup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseOAuthUser {
    private Long userId;
    private String nickname;
    private String email;
    private String roles;
    private String provider;
    private String specialty;


}

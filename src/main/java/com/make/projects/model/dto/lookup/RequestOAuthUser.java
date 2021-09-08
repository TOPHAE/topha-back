package com.make.projects.model.dto.lookup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RequestOAuthUser {


    private String nickname;
    private String tech;
}

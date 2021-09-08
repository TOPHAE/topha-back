package com.make.projects.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@Builder
public class RequestUpdateUser {

    @NotBlank(message = "닉네임 필수 입력")
    private String nickname;
    @NotBlank(message = "전문 분야 선택")
    private String tech;
}

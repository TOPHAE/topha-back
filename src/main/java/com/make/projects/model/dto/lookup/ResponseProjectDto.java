package com.make.projects.model.dto.lookup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
public class ResponseProjectDto {

    private Long userId;
    private String nickname;
    private String title;



}

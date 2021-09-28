package com.make.projects.model.dto.lookup;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class ProjectQueryDto {
    private Long project_Id;
    private String title;
    private String nickname;
    private String user_Spec;
    private Integer view_Count;
    private String img_Url;
    private String roles;
    private Integer like_Count;
    private Set<String> tech;
    private Set<String> spec;

    @Builder
    public ProjectQueryDto(Long project_Id, String title, String nickname, String user_Spec, Integer view_Count, String img_Url, String roles, Integer like_Count, Set<String> tech, Set<String> spec) {
        this.project_Id = project_Id;
        this.title = title;
        this.nickname = nickname;
        this.user_Spec = user_Spec;
        this.view_Count = view_Count;
        this.img_Url = img_Url;
        this.roles = roles;
        this.like_Count = like_Count;
        this.tech = tech;
        this.spec = spec;
    }
}


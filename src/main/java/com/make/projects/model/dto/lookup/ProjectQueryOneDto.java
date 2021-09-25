package com.make.projects.model.dto.lookup;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectQueryOneDto {

    private Long project_Id;
    private String title;
    private String nickname;
    private String user_Spec;
    private Integer like_Count;
    private Integer view_Count;
    private Set<String> tech;
    private Set<String> spec;
    private String img_Url;
    private String roles;
    private List<CommentQueryDto> commentQueryDtos;

    @Builder
    public ProjectQueryOneDto(Long project_Id, String title, String nickname, String user_Spec, Integer like_Count, Integer view_Count, Set<String> tech, Set<String> spec, String img_Url, String roles) {
        this.project_Id = project_Id;
        this.title = title;
        this.nickname = nickname;
        this.user_Spec = user_Spec;
        this.like_Count = like_Count;
        this.view_Count = view_Count;
        this.tech = tech;
        this.spec = spec;
        this.img_Url = img_Url;
        this.roles = roles;
    }
}

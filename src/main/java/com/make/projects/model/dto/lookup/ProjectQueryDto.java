package com.make.projects.model.dto.lookup;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Set;



@Data
public class ProjectQueryDto {

    private Long project_Id;
    private String title;
    private String nickname;
    private String user_Spec;
    private Integer like_Count;
    private Integer view_Count;
    private Set<String> tech;
    private Set<String> spec;
    private List<CommentQueryDto> comments;


    @Builder
    @QueryProjection
    public ProjectQueryDto(Long project_Id, String title, String nickname, String user_Spec, Integer like_Count, Integer view_Count,  Set<String> tech, Set<String> spec) {
        this.project_Id = project_Id;
        this.title = title;
        this.nickname = nickname;
        this.user_Spec = user_Spec;
        this.like_Count = like_Count;
        this.view_Count = view_Count;
        this.tech = tech;
        this.spec = spec;
    }
}


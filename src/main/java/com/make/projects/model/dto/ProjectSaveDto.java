package com.make.projects.model.dto;

import com.make.projects.model.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSaveDto {

    private String title;
    private String nickname;
    private Integer likeCount;
    private Integer viewCount;
    private String userSpec;
    private Set<String> tech;
    private Set<String> spec;
    private Long userId;
    

    public Project.ProjectBuilder toEntity(){

       return Project.builder()
                .nickname(nickname)
                .spec(spec)
                .userSpec(userSpec)
                .tech(tech)
                .viewCount(viewCount)
                .likeCount(likeCount)
                .title(title);
    }
}

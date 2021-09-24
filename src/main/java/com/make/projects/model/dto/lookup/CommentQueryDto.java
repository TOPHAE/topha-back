package com.make.projects.model.dto.lookup;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentQueryDto {
    private Long project_Id;
    private String content;



    @QueryProjection
    public CommentQueryDto(Long project_Id, String content) {
        this.project_Id = project_Id;
        this.content = content;

    }
}


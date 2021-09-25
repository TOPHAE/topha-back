package com.make.projects.model.dto.lookup;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentQueryDto {
    private Long project_Id;
    private Long commentId;
    private String content;



    @QueryProjection
    public CommentQueryDto(Long project_Id, Long commentId, String content) {
        this.project_Id = project_Id;
        this.commentId = commentId;
        this.content = content;
    }
}


package com.make.projects.model.dto.lookup;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class CommentQueryDto {
    private Long project_Id;
    private String nickname;
    private String content;



    @QueryProjection
    public CommentQueryDto(Long project_Id, String nickname, String content) {
        this.project_Id = project_Id;
        this.nickname = nickname;
        this.content = content;

    }
}


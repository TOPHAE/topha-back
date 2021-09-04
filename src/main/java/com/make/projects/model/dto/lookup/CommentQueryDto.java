package com.make.projects.model.dto.lookup;

import lombok.Data;

@Data
public class CommentQueryDto {

    private Long projectId;
    private String content;

    public CommentQueryDto(Long projectId, String content) {
        this.projectId = projectId;
        this.content = content;
    }
}

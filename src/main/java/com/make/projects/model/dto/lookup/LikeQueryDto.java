package com.make.projects.model.dto.lookup;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikeQueryDto {
    private Long like_id;
    private Long project_id;
    private Long user_id;

    @Builder
    @QueryProjection
    public LikeQueryDto(Long like_id, Long project_id, Long user_id) {
        this.like_id = like_id;
        this.project_id = project_id;
        this.user_id = user_id;
    }
}

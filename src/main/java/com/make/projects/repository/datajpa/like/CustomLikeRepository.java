package com.make.projects.repository.datajpa.like;

import com.make.projects.model.dto.lookup.LikeQueryDto;


import java.util.List;

public interface CustomLikeRepository{
    List<LikeQueryDto> selectAllLikes(List<Long> ids);
}

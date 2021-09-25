package com.make.projects.repository.datajpa;
import com.make.projects.model.dto.lookup.CommentQueryDto;

import java.util.List;

public interface CustomCommentRepository {

    List<CommentQueryDto> selectComments(Long projectId);
}

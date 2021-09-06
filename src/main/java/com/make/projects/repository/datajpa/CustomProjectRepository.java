package com.make.projects.repository.datajpa;

import com.make.projects.model.domain.Project;
import com.make.projects.model.dto.lookup.CommentQueryDto;
import com.make.projects.model.dto.lookup.ProjectQueryDto;

import java.util.List;

public interface CustomProjectRepository {

    Project selectOneProject(Long projectId);
    List<CommentQueryDto> selectOneComment(Long projectId);
    List<Project> selectAllProject();
    List<CommentQueryDto> selectAllComment(List<Long> projectId);
}

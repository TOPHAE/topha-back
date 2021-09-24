package com.make.projects.repository.datajpa;

import com.make.projects.model.domain.Project;
import com.make.projects.model.dto.lookup.ProjectQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomProjectRepository {

    Project selectOneProject(Long projectId);
    Page<ProjectQueryDto> selectAllProject(Pageable pageable);
}

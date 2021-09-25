package com.make.projects.repository.datajpa;

import com.make.projects.model.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomProjectRepository {

    Project selectOneProject(Long projectId);
    Page<Project> selectAllProject(Pageable pageable);
}

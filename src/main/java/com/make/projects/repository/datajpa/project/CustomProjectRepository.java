package com.make.projects.repository.datajpa.project;

import com.make.projects.model.domain.Project;
import com.make.projects.model.dto.ProjectConditionSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomProjectRepository {

    Project selectOneProject(Long projectId);
    Page<Project> selectAllProject(Pageable pageable, ProjectConditionSearch projectConditionSearch);
}
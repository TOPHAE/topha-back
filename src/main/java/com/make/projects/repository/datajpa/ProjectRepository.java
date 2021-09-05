package com.make.projects.repository.datajpa;

import com.make.projects.model.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long>,CustomProjectRepository {

}

package com.make.projects.repository;

import com.make.projects.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}

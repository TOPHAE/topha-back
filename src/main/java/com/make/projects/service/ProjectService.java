package com.make.projects.service;

import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.model.domain.Project;
import com.make.projects.model.dto.ProjectSaveDto;
import com.make.projects.model.dto.lookup.CommentQueryDto;
import com.make.projects.model.dto.lookup.ProjectQueryDto;
import com.make.projects.model.dto.lookup.ResponseProjectDto;
import com.make.projects.repository.datajpa.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public ResponseProjectDto saveProject(ProjectSaveDto projectSaveDto, CustomUserDetails customUserDetails) {

        Project projects = Project.builder()
                .viewCount(0)
                .likeCount(0)
                .content(projectSaveDto.getContent())
                .title(projectSaveDto.getTitle())
                .tech(projectSaveDto.getTech().stream().map(ProjectSaveDto.techs::getKey).collect(Collectors.toSet()))
                .spec(projectSaveDto.getSpec().stream().map(ProjectSaveDto.specs::getKey).collect(Collectors.toSet()))
                .userSpec(customUserDetails.getUser().getSpecialty())
                .nickname(customUserDetails.getUser().getNickname())
                .user(customUserDetails.getUser())
                .build();

        Project project = projectRepository.save(projects);


        return ResponseProjectDto.builder()
                .userId(project.getUser().getUserId())
                .nickname(project.getNickname())
                .title(project.getTitle())
                .build();



    }


    @Transactional
    public ProjectQueryDto selectOne(Long projectId) {
        Project project = projectRepository.selectOneProject(projectId);
        project.increateViewCount();
        Long id = project.getId();

        return ProjectQueryDto.builder()
                .project_Id(project.getId())
                .tech(project.getTech())
                .user_Spec(project.getUserSpec())
                .title(project.getTitle())
                .like_Count(project.getLikeCount())
                .view_Count(project.getViewCount())
                .spec(project.getSpec())
                .nickname(project.getNickname())
                .build();
    }

    public Page<ProjectQueryDto> selectAll(Pageable pageable){
        return projectRepository.selectAllProject(pageable);


    }
}
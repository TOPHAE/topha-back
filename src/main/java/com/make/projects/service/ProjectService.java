package com.make.projects.service;

import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.model.domain.Like;
import com.make.projects.model.domain.Project;
import com.make.projects.model.dto.ProjectConditionSearch;
import com.make.projects.model.dto.ProjectSaveDto;
import com.make.projects.model.dto.lookup.*;
import com.make.projects.repository.datajpa.comment.CommentRepository;
import com.make.projects.repository.datajpa.like.LikeRepository;
import com.make.projects.repository.datajpa.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProjectService {

    private final EntityManager em;
    private final ProjectRepository projectRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

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
    public ProjectQueryOneDto selectOne(Long projectId) {

        Project project = projectRepository.selectOneProject(projectId);
        Long projectIds = project.getId();
        project.increateViewCount();
        List<CommentQueryDto> commentQueryDtos =
                commentRepository.selectComments(projectIds);

        return ProjectQueryOneDto
                .builder()
                .project_Id(project.getId())
                .nickname(project.getNickname())
                .spec(project.getSpec())
                .tech(project.getTech())
                .title(project.getTitle())
                .user_Spec(project.getUserSpec())
                .like_Count(project.getLikeCount())
                .view_Count(project.getViewCount())
                .img_Url(project.getUser().getImgUrl())
                .roles(project.getUser().getRoles())
                .commentQueryDtos(commentQueryDtos)
                .build();
    }

    public Set<ProjectQueryDto> selectAll(Pageable pageable, ProjectConditionSearch projectConditionSearch) {
        boolean isJavascript = false;
        boolean isSpring = false;
        boolean isPhp = false;
        boolean isReact = false;
        if (projectConditionSearch.getTechcondition().contains("javascript"))
            isJavascript = true;
        if (projectConditionSearch.getTechcondition().contains("spring"))
            isSpring = true;
        if (projectConditionSearch.getTechcondition().contains("php"))
            isPhp = true;
        if (projectConditionSearch.getTechcondition().contains("react"))
            isReact = true;
      /*  boolean isJavascript = false;
        boolean isTypescript = false;
        boolean isJava = false;
        boolean isPhp = false;
        boolean isFlutter = false;
        boolean isSwift = false;
        boolean isCpp = false;
        boolean isCsharp = false;

        if (projectConditionSearch.getTechcondition().contains("javascript"))
            isJavascript = true;
        if (projectConditionSearch.getTechcondition().contains("typescript"))
            isTypescript = true;
        if (projectConditionSearch.getTechcondition().contains("java"))
            isJava = true;
        if (projectConditionSearch.getTechcondition().contains("php"))
            isPhp = true;
        if (projectConditionSearch.getTechcondition().contains("flutter"))
            isFlutter = true;
        if (projectConditionSearch.getTechcondition().contains("swift"))
            isSwift = true;
        if (projectConditionSearch.getTechcondition().contains("cpp"))
            isCpp = true;
        if (projectConditionSearch.getTechcondition().contains("csharp"))
            isCsharp = true;*/


        Page<Project> projects = projectRepository.selectAllProject(pageable, projectConditionSearch, isJavascript,isSpring,isPhp);
        return projects.stream().map(s -> {
            return ProjectQueryDto
                    .builder()
                    .project_Id(s.getId())
                    .nickname(s.getNickname())
                    .user_Spec(s.getUserSpec())
                    .view_Count(s.getViewCount())
                    .like_Count(s.getLikeCount())
                    .title(s.getTitle())
                    .spec(s.getSpec())
                    .tech(s.getTech())
                    .img_Url(s.getUser().getImgUrl())
                    .roles(s.getUser().getRoles())
                    .build();

        }).collect(Collectors.toSet());
    }
}
package com.make.projects.service;

import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.model.domain.Project;
import com.make.projects.model.dto.ProjectSaveDto;
import com.make.projects.model.dto.lookup.CommentQueryDto;
import com.make.projects.model.dto.lookup.ProjectQueryDto;
import com.make.projects.model.dto.lookup.ProjectQueryOneDto;
import com.make.projects.model.dto.lookup.ResponseProjectDto;
import com.make.projects.repository.datajpa.CommentRepository;
import com.make.projects.repository.datajpa.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
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
        ProjectQueryOneDto projectQueryOneDto = ProjectQueryOneDto
                .builder()
                .project_Id(project.getId())
                .like_Count(project.getLikeCount())
                .nickname(project.getNickname())
                .spec(project.getSpec())
                .tech(project.getTech())
                .title(project.getTitle())
                .user_Spec(project.getUserSpec())
                .view_Count(project.getViewCount())
                .img_Url(project.getUser().getImgUrl())
                .roles(project.getUser().getRoles())
                .commentQueryDtos(commentQueryDtos)
                .build();

        return projectQueryOneDto;
    }

    public List<ProjectQueryDto> selectAll(Pageable pageable){

        Page<Project> projects = projectRepository.selectAllProject(pageable);
        List<ProjectQueryDto> collect = projects.stream().map(s -> {
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

        }).collect(Collectors.toList());
        return collect;
    }
}
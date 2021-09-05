package com.make.projects.service;

import com.make.projects.model.domain.Project;
import com.make.projects.model.dto.lookup.CommentQueryDto;
import com.make.projects.model.dto.lookup.ProjectQueryDto;
import com.make.projects.repository.datajpa.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;


    @Transactional
    public ProjectQueryDto selectOne(Long projectId) {
        Project project = projectRepository.selectOneProject(projectId);

        Long id = project.getId();
        List<CommentQueryDto> comments = projectRepository.selectOneComment(id);
        Map<Long, List<CommentQueryDto>> IdListMap = comments.stream().collect(Collectors.groupingBy(CommentQueryDto::getProject_Id));

        ProjectQueryDto projectQueryDto = ProjectQueryDto.builder()
                .project_Id(project.getId())
                .tech(project.getTech())
                .user_Spec(project.getUserSpec())
                .title(project.getTitle())
                .like_Count(project.getLikeCount())
                .view_Count(project.getViewCount())
                .spec(project.getSpec())
                .nickname(project.getNickname())
                .build();
        projectQueryDto.setComments(IdListMap.get(id));
        return projectQueryDto;

    }
}
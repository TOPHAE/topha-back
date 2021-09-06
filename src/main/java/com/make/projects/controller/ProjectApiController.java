package com.make.projects.controller;


import com.make.projects.model.domain.Project;
import com.make.projects.model.dto.ProjectSaveDto;
import com.make.projects.model.dto.Result;
import com.make.projects.model.dto.lookup.ProjectQueryDto;
import com.make.projects.repository.datajpa.ProjectRepository;
import com.make.projects.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProjectApiController {

    private final ProjectRepository projectRepository;
    private final ProjectService projectService;


    //프로젝트 게시물 단일조회
    @GetMapping("/project/selectOne/{projectId}")
    public Result<ProjectQueryDto> projectSelectOne(@PathVariable Long projectId){

        ProjectQueryDto projectQueryDto = projectService.selectOne(projectId);
        return new Result<>(projectQueryDto,HttpStatus.OK.value());
    }


    //프로젝트 게시물 전체조회
    @GetMapping("/project/selectAll")
    public Result<List<ProjectQueryDto>> projectSelectAll(){
        List<ProjectQueryDto> projectQueryDtos = projectService.selectAll();
        return new Result<>(projectQueryDtos,HttpStatus.OK.value());
    }

    //프로젝트 게시물 등록
    @PostMapping("/project/save/{userId}")
    public ResponseEntity<Project> projectSave(@Valid @RequestBody ProjectSaveDto project, @PathVariable Long userId){
        return null;
    }




}

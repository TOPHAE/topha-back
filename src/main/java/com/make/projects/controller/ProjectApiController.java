package com.make.projects.controller;


import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.model.dto.ProjectSaveDto;
import com.make.projects.model.dto.Result;
import com.make.projects.model.dto.lookup.ProjectQueryDto;
import com.make.projects.model.dto.lookup.ResponseProjectDto;
import com.make.projects.repository.datajpa.CommentRepository;
import com.make.projects.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProjectApiController {

    private final ProjectService projectService;

    //프로젝트 게시물 단일조회
    @GetMapping("/project/selectOne/{projectId}")
    public Result<ProjectQueryDto> projectSelectOne(@PathVariable Long projectId){

        ProjectQueryDto projectQueryDto = projectService.selectOne(projectId);
        return new Result<>(projectQueryDto,HttpStatus.OK.value());
    }


    //프로젝트 게시물 전체조회
    @GetMapping("/project/selectAll")
    public Result<Page<ProjectQueryDto>> projectSelectAll(@PageableDefault(size = 10) Pageable pageable){
        Page<ProjectQueryDto> projectQueryDtos = projectService.selectAll(pageable);
        return new Result<>(projectQueryDtos,HttpStatus.OK.value());
    }

    //프로젝트 게시물 등록
    @PostMapping("/project/save")
    public Result<ResponseProjectDto> projectSave(@Valid @RequestBody ProjectSaveDto project, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("ProjectSaveDto={}",project);
        ResponseProjectDto responseProjectDto = projectService.saveProject(project, customUserDetails);
        return new Result<>(responseProjectDto, HttpStatus.OK.value());

    }




}

package com.make.projects.controller;


import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.model.dto.ProjectConditionSearch;
import com.make.projects.model.dto.ProjectSaveDto;
import com.make.projects.model.dto.Result;
import com.make.projects.model.dto.lookup.ProjectQueryOneDto;
import com.make.projects.model.dto.lookup.ResponseProjectDto;
import com.make.projects.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProjectApiController {

    private final ProjectService projectService;


    @GetMapping("/project/selectOne/{projectId}")
    public Result<ProjectQueryOneDto> projectSelectOne(@PathVariable Long projectId){
        return new Result<>( projectService.selectOne(projectId),HttpStatus.OK.value());
    }

    /*https://footprint-of-nawin.tistory.com/71
    * 원인
    */
    /*@PageableDefault(size = 10 , sort = "create_date", direction = Sort.Direction.DESC)*/
    //프로젝트 게시물 전체조회
    @PostMapping(value = "/project/selectAll")
    public Result<?> projectSelectAll(@RequestBody ProjectConditionSearch projectConditionSearch,
                                      @PageableDefault(size = 10) Pageable pageable){
        return new Result<>(projectService.selectAll(pageable,projectConditionSearch), HttpStatus.OK.value());
    }


    @PostMapping("/project/save")
    public Result<ResponseProjectDto> projectSave(@Valid @RequestBody ProjectSaveDto project, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("ProjectSaveDto={}",project);
        return new Result<>( projectService.saveProject(project, customUserDetails), HttpStatus.OK.value());

    }

}

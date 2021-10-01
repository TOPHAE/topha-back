package com.make.projects.controller;


import com.make.projects.config.auth.CustomUserDetails;
import com.make.projects.model.dto.ProjectConditionSearch;
import com.make.projects.model.dto.ProjectSaveDto;
import com.make.projects.model.dto.Result;
import com.make.projects.model.dto.lookup.ProjectQueryDto;
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
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProjectApiController {

    private final ProjectService projectService;

    //프로젝트 게시물 단일조회
    @GetMapping("/project/selectOne/{projectId}")
    public Result<ProjectQueryOneDto> projectSelectOne(@PathVariable Long projectId){

        ProjectQueryOneDto projectQueryDto = projectService.selectOne(projectId);
        return new Result<>(projectQueryDto,HttpStatus.OK.value());
    }

    /*https://footprint-of-nawin.tistory.com/71
    * 원인
    */
    /*@PageableDefault(size = 10 , sort = "create_date", direction = Sort.Direction.DESC)*/
    //프로젝트 게시물 전체조회
    @PostMapping(value = "/project/selectAll",produces = "application/json")
    public Result<?> projectSelectAll(@RequestBody ProjectConditionSearch projectConditionSearch,
                                      @PageableDefault(size = 10) Pageable pageable){
        List<ProjectQueryDto> projectQueryDtos = projectService.selectAll(pageable,projectConditionSearch);

        return new Result<>(projectQueryDtos, HttpStatus.OK.value());
    }

    //프로젝트 게시물 등록
    @PostMapping("/project/save")
    public Result<ResponseProjectDto> projectSave(@Valid @RequestBody ProjectSaveDto project, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("ProjectSaveDto={}",project);
        ResponseProjectDto responseProjectDto = projectService.saveProject(project, customUserDetails);
        return new Result<>(responseProjectDto, HttpStatus.OK.value());

    }

}

package com.make.projects.controller;

import com.make.projects.model.domain.Project;
import com.make.projects.model.dto.ResponseDto;
import com.make.projects.repository.ProjectRepository;
import com.make.projects.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProjectApiController {

    private final ProjectRepository projectRepository;

    @PostMapping("/project/save/{userId}")
    public ResponseEntity<Project> projectSave(@RequestBody Project project, @PathVariable Long userId){
        projectRepository.save(project);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/project/selectAll")
    public ResponseDto<List<Project>> projectSelect(){
        List<Project> all = projectRepository.findAll();
        return new ResponseDto<List<Project>>(all,HttpStatus.OK.value());
    }


}

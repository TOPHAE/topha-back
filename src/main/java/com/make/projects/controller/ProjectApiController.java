package com.make.projects.controller;

import com.make.projects.model.domain.Project;
import com.make.projects.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ProjectApiController {

    private final ProjectRepository projectRepository;


    @PostMapping("/project/save")
    public ResponseEntity<Project> projectSave(@RequestBody Project project){
        projectRepository.save(project);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/project/selectAll")
    public ResponseEntity<List<Project>> projectSelect(){

        return new ResponseEntity<>(projectRepository.findAll(),HttpStatus.OK);
    }


}

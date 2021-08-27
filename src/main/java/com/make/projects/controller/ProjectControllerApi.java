package com.make.projects.controller;

import com.make.projects.entity.Project;
import com.make.projects.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ProjectControllerApi {

    private final ProjectRepository projectRepository;


    @PostMapping("/api/project/save")
    public ResponseEntity<Project> projectSave(@RequestBody Project project){
        projectRepository.save(project);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/project/selectAll")
    public ResponseEntity<List<Project>> projectSelect(){

        return new ResponseEntity<>(projectRepository.findAll(),HttpStatus.OK);
    }


}

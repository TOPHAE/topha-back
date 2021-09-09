package com.make.projects.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSaveDto {

    private String title;
    private Set<String> tech;
    private Set<String> spec;
    private String content;
    

}

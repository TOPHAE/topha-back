package com.make.projects.model.dto;

import lombok.*;

import java.util.Set;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectSaveDto {

    private String title;
    private Set<techs> tech;
    private Set<specs> spec;
    private String content;


    @Data
    public static class specs {
        private Long id;
        private String key;
        private String value;
    }


    @Data
    public static class techs {
        private Long id;
        private String key;
    }

}


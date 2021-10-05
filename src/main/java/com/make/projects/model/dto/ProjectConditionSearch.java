package com.make.projects.model.dto;

import com.make.projects.model.dto.lookup.Tech;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectConditionSearch {

    private String sortingcondition;
    private Set<Tech> techcondition;

}

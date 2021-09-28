package com.make.projects.model.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ProjectConditionSearch {

    private String sortingcondition;

}

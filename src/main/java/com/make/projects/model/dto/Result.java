package com.make.projects.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {
    T data;
    Integer httpStatus;
    String errorMessage;

    public Result(T data, Integer httpStatus) {
        this.data = data;
        this.httpStatus = httpStatus;
    }

    public Result( Integer httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

}

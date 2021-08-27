package com.make.projects.entity.date;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class InheritTime {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;
/*
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;*/
}

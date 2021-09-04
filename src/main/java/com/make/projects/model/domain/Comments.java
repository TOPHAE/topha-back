package com.make.projects.model.domain;

import com.nimbusds.jose.shaded.json.annotate.JsonIgnore;
import lombok.*;
import javax.persistence.*;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comments extends CommonDate {

    @Id
    @GeneratedValue
    private Long commentId;

    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_Id")
    private Project project;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users user;

    public void setCommentProject(Project project){
        if(this.project != null){
            this.project.getComments().remove(this);
        }
        this.project = project;
        project.getComments().add(this);
    }
}

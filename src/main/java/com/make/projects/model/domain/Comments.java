package com.make.projects.model.domain;

import com.nimbusds.jose.shaded.json.annotate.JsonIgnore;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;


@Builder
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

    public void setCommentUser(Users user){
        if(this.user != null){
            this.user.getComments().remove(this);
        }
        this.user = user;
        user.getComments().add(this);
    }

    public void setCommentProject(Project project){
        if(this.project != null){
            this.project.getComments().remove(this);
        }
        this.project = project;
        project.getComments().add(this);
    }
}

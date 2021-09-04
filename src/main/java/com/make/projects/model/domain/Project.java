package com.make.projects.model.domain;

import com.nimbusds.jose.shaded.json.annotate.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Project extends CommonDate {

    @Id
    @GeneratedValue
    @Column(name = "projectId")
    private Long id;

    private String title;
    private String nickname;
    //@Column(name = "LIKE_COUNT")
    private Integer likeCount=0;

   // @Column(name = "VIEW_COUNT")
    private Integer viewCount=0;

    //@Column(name = "USER_SPEC")
    private String userSpec;

    @ElementCollection //값 타입 컬렉션입니다
    @CollectionTable(name ="TECH",joinColumns = @JoinColumn(name = "projectId")) //컬렉션 테이블 생성,외래키로지정 만들어질 테이블에
    @Column(name = "TECH")
    private Set<String> tech = new HashSet<>();

    @ElementCollection //값 타입 컬렉션입니다
    @CollectionTable(name ="SPEC",joinColumns = @JoinColumn(name = "projectId")) //컬렉션 테이블 생성,외래키로지정 만들어질 테이블에
    @Column(name = "SPEC")
    private Set<String> spec = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private Users user;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    private List<Comments> comments = new ArrayList<>();

    public void setProjectComment(Comments comment){
        comment.setProject(this);
        comments.add(comment);
    }

    public int increateViewCount(){
        this.viewCount = viewCount + 1;
        return viewCount;
    }


}
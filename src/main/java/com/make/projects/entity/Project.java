package com.make.projects.entity;

import com.make.projects.entity.date.InheritTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Project extends InheritTime {

    @Id
    @GeneratedValue
    @Column(name = "PROJECTID")
    private Long id;

    private String title;
    private String nickname;
    //@Column(name = "LIKE_COUNT")
    private Integer likeCount;

   // @Column(name = "VIEW_COUNT")
    private Integer viewCount;

    //@Column(name = "USER_SPEC")
    private String userSpec;

    @ElementCollection //값 타입 컬렉션입니다
    @CollectionTable(name ="TECH",joinColumns = @JoinColumn(name = "PROJECTID")) //컬렉션 테이블 생성,외래키로지정 만들어질 테이블에
    @Column(name = "TECH")
    private Set<String> tech = new HashSet<>();

    @ElementCollection //값 타입 컬렉션입니다
    @CollectionTable(name ="SPEC",joinColumns = @JoinColumn(name = "PROJECTID")) //컬렉션 테이블 생성,외래키로지정 만들어질 테이블에
    @Column(name = "SPEC")
    private Set<String> spec = new HashSet<>();


}
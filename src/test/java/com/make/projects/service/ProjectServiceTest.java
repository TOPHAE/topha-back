package com.make.projects.service;

import com.make.projects.model.domain.Project;
import com.make.projects.model.domain.Users;
import com.make.projects.model.domain.enumType.Provider;
import com.make.projects.repository.datajpa.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProjectServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectService projectService;

    @Test
    @Rollback(value = false)
    public void 프로젝트생성(){
        Users users = Users.builder()
                .specialty("front")
                .nickname("개발자")
                .email("woojin126@naver.com")
                .roles("ROLE_USER")
                .provider(Provider.google)
                .build();
        em.persist(users);
        HashSet set = new HashSet();
        set.add("java");

        Project project = Project.builder()
                .spec(set)
                .tech(set)
                .title("하이")
                .nickname(users.getNickname())
                .userSpec(users.getSpecialty())
                .user(users)
                .build();

        em.persist(project);



    }
}
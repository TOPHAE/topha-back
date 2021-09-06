package com.make.projects.controller;

import com.make.projects.repository.datajpa.CommentRepository;
import com.make.projects.repository.datajpa.ProjectRepository;
import com.make.projects.repository.datajpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;


@SpringBootTest
@Transactional
class ProjectApiControllerTest {

    @Autowired
    EntityManager em;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;

    //@Test
    @Rollback(value = false)
    void 프로젝트단일조회(){
      /*  Users users = Users.builder()
                .nickname("김우진")
                .provider(Provider.google)
                .roles("ROLE_USER")
                .email("woojin126@naver.com")
                .specialty("백앤드")
                .build();

        em.persist(users);

        Comments comment = Comments.builder()
                .content("와 이프로젝트 저도 같이 할래요!!!")
                .build();

        em.persist(comment);


        Set<String> spec = new HashSet<>();
        spec.add("백엔드");
        Set<String> tech = new HashSet<>();
        tech.add("java");
        tech.add("spring");

        List<Comments> list = new ArrayList<>();
        list.add(comment);
        Project project = Project.builder()
                .user(users)
                .nickname(users.getNickname())
                .spec(spec)
                .userSpec("백엔드")
                .tech(tech)
                .viewCount(10)
                .title("우리프로젝트오세요~")
                .likeCount(10)
                .comments(list)
                .build();

        em.persist(project);
        em.flush();

*/



    }
}
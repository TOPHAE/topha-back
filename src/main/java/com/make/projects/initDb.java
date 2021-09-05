package com.make.projects;

import com.make.projects.model.domain.Comments;
import com.make.projects.model.domain.Project;
import com.make.projects.model.domain.Users;
import com.make.projects.model.domain.enumType.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Component
public class initDb {

    private final InitService initService;

    @Autowired
    public initDb(InitService initService){
        this.initService = initService;
    }

    @PostConstruct
    public void init(){
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

       public void dbInit1() {

           Users users = Users.builder()
                   .nickname("김우진")
                   .provider(Provider.google)
                   .roles("ROLE_USER")
                   .email("woojin126@naver.com")
                   .specialty("백앤드")
                   .build();

           em.persist(users);


           Set<String> spec = new HashSet<>();
           spec.add("백엔드");
           Set<String> tech = new HashSet<>();
           tech.add("java");
           tech.add("spring");


           Project project = Project.builder()
                   .user(users)
                   .nickname(users.getNickname())
                   .spec(spec)
                   .userSpec("백엔드")
                   .tech(tech)
                   .viewCount(10)
                   .title("우리프로젝트오세요~")
                   .likeCount(10)
                   .build();


           em.persist(project);

           Comments comment = new Comments("안녕하세연");
           em.persist(comment);
           Comments comment1 = new Comments("보이루");
           em.persist(comment1);

           comment.setCommentProject(project);
           comment1.setCommentProject(project);

        }


    }

}

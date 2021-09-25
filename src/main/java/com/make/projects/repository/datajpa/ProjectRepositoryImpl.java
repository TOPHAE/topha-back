package com.make.projects.repository.datajpa;

import com.make.projects.model.domain.*;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import static com.make.projects.model.domain.QProject.project;
import static com.make.projects.model.domain.QUsers.users;

public class ProjectRepositoryImpl implements CustomProjectRepository{

    @PersistenceContext
    EntityManager em;
    private JPAQueryFactory queryFactory;

    public ProjectRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Project selectOneProject(Long projectId) {
       return queryFactory
                .select(project)
                .from(project)
                .join(project.user, users)
                .fetchJoin()
                .where(project.id.eq(projectId))
                .fetchOne();
    }


    @Override
    public Page<Project> selectAllProject(Pageable pageable) {

        QueryResults<Project> result = queryFactory
                .select(project)
                .from(project)
                .leftJoin(project.user, users)
                .where(project.user.userId.eq(users.userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Project> content = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

}

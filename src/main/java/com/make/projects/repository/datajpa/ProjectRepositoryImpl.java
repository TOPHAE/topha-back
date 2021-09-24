package com.make.projects.repository.datajpa;

import com.make.projects.model.domain.*;
import com.make.projects.model.dto.lookup.ProjectQueryDto;
import com.make.projects.model.dto.lookup.QProjectQueryDto;
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
                .where(project.id.in(projectId))
                .fetchOne();
    }



    @Override
    public Page<ProjectQueryDto> selectAllProject(Pageable pageable) {

        QueryResults<ProjectQueryDto> result = queryFactory
                .select(new QProjectQueryDto(
                        project.id,
                        project.title,
                        users.nickname,
                        project.userSpec,
                        project.likeCount,
                        project.viewCount,
                        project.tech,
                        project.spec
                ))
                .from(project)
                .leftJoin(project.user, users)
                .where(project.user.userId.eq(users.userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ProjectQueryDto> content = result.getResults();
        long total = result.getTotal();//count 숫자

        return new PageImpl<>(content,pageable,total);
    }



   /* @Override
    public List<CommentQueryDto> selectAllComment(List<Long> projectId) {
        return queryFactory
                .select(new QCommentQueryDto(
                        project.id, comments.content
                ))
                .from(comments)
                .leftJoin(comments.project, project)
                .where(comments.project.id.in(projectId))
                .fetch();
    }*/


}

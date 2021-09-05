package com.make.projects.repository.datajpa;

import com.make.projects.model.domain.Comments;
import com.make.projects.model.domain.Project;
import com.make.projects.model.domain.QComments;
import com.make.projects.model.dto.lookup.CommentQueryDto;
import com.make.projects.model.dto.lookup.ProjectQueryDto;
import com.make.projects.model.dto.lookup.QCommentQueryDto;
import com.make.projects.model.dto.lookup.QProjectQueryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.make.projects.model.domain.QComments.comments;
import static com.make.projects.model.domain.QProject.project;

public class CustomProjectRepositoryImpl implements CustomProjectRepository{

    @PersistenceContext
    EntityManager em;
    private JPAQueryFactory queryFactory;

    public CustomProjectRepositoryImpl(EntityManager em) {
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
    public List<CommentQueryDto> selectOneComment(Long projectId) {
 return queryFactory
              .select(new QCommentQueryDto(
                      project.id , comments.content
              ))
              .from(comments)
              .leftJoin(comments.project, project)
              .where(comments.project.id.in(projectId))
              .fetch();
    }


}

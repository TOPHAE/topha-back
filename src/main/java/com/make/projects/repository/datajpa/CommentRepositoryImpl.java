package com.make.projects.repository.datajpa;

import com.make.projects.model.dto.lookup.CommentQueryDto;
import com.make.projects.model.dto.lookup.QCommentQueryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.make.projects.model.domain.QComments.comments;
import static com.make.projects.model.domain.QProject.project;

public class CommentRepositoryImpl implements CustomCommentRepository{

    @PersistenceContext
    EntityManager em;
    private JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<CommentQueryDto> selectComments(Long projectId) {
     return  queryFactory
                .select(new QCommentQueryDto(
                        project.id,
                        comments.commentId,
                        comments.content
                ))
              .from(comments)
              .leftJoin(comments.project, project)
              .where(comments.project.id.in(projectId))
              .fetch();

    }
}

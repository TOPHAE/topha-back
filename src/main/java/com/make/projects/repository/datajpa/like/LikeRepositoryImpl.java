package com.make.projects.repository.datajpa.like;

import com.make.projects.model.dto.lookup.LikeQueryDto;
import com.make.projects.model.dto.lookup.QLikeQueryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.make.projects.model.domain.QLike.like;

public class LikeRepositoryImpl implements CustomLikeRepository {

    @PersistenceContext
    EntityManager em;

    private final JPAQueryFactory queryFactory;

    public LikeRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<LikeQueryDto> selectAllLikes(List<Long> projectIds) {
        return queryFactory
                .select(new QLikeQueryDto(
                        like.project.id,like.project.user.userId,
                        like.likeId
                ))
                .from(like)
                .where(like.project.id.in(projectIds))
                .fetch();
    }
}

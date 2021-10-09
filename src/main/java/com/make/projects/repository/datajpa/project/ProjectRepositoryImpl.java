package com.make.projects.repository.datajpa.project;

import com.make.projects.model.domain.*;
import com.make.projects.model.dto.ProjectConditionSearch;
import com.make.projects.model.dto.lookup.Tech;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.make.projects.model.domain.QProject.project;
import static com.make.projects.model.domain.QUsers.users;

public class ProjectRepositoryImpl implements CustomProjectRepository {

    @PersistenceContext
    EntityManager em;
    private final JPAQueryFactory queryFactory;

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
    public Page<Project> selectAllProject(Pageable pageable, ProjectConditionSearch projectConditionSearch,boolean isjava,boolean isSpring, boolean isPhp) {

        QueryResults<Project> result = queryFactory
                .select(project)
                .from(project)
                .leftJoin(project.user, users)
                .on(project.user.userId.eq(users.userId))
                .where(
                        techNameEq(projectConditionSearch.getTechcondition(),isjava,isSpring,isPhp)
                )
                .orderBy(
                        SortingEq(projectConditionSearch.getSortingcondition())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Project> content = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression techNameEq(Set<Tech> techCondition,boolean isjava,boolean isSpring, boolean isPhp) {
        if(isjava) {
            for (Tech tech : techCondition) {
                project.tech.contains(tech.getTechItem());
            }
        }
        if(isSpring) {
            for (Tech tech : techCondition) {
                project.tech.contains(tech.getTechItem());
            }
        }
        if(isPhp) {
            for (Tech tech : techCondition) {
                project.tech.contains(tech.getTechItem());
            }
        }
        return null;
        //return Optional.ofNullable(techCondition).map(str -> project.tech.contains(Expressions.t)).orElse(null);
    }


    private OrderSpecifier SortingEq(String sortName) {

       Optional.ofNullable(sortName).stream().filter(Objects::nonNull)
                .map(s ->{
                    if(s.equals("like"))
                        return project.likes.size().desc();
                    else if (s.equals("recent"))
                        return project.createDate.desc();
                    return project.createDate.desc();
                });
        return project.createDate.desc();
    }
}
package com.make.projects.repository.datajpa.like;

import com.make.projects.model.domain.Like;
import com.make.projects.model.domain.Project;
import com.make.projects.model.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long>, CustomLikeRepository {

    Optional<Integer> countByProject(Project project);
    //유저와, 프로젝트 인자로 받아 해당 게시물에, 해당 회원이 좋아요를 등록한적이 있는지 체크하는 용도
    Optional<Like> findByUsersAndProject(Users users , Project project);
}

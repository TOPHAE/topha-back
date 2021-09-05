package com.make.projects.repository.datajpa;

import com.make.projects.model.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comments,Long> {

}

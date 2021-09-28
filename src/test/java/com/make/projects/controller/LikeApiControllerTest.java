package com.make.projects.controller;

import com.make.projects.model.domain.Like;
import com.make.projects.model.domain.Project;
import com.make.projects.model.domain.Users;
import com.make.projects.repository.datajpa.like.LikeRepository;
import com.make.projects.repository.datajpa.project.ProjectRepository;
import com.make.projects.repository.datajpa.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class LikeApiControllerTest {

    @Autowired
    EntityManager em;

    @Autowired
    UserRepository userRepository;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProjectRepository projectRepository;

    
    @DisplayName("좋아요 테스트")
    @Rollback(value = false)
    @WithMockUser
    @Test
    void testCreateLike() throws Exception {
        Project project = addRecipe();
        mockMvc.perform(post("/api/like/"+project.getId()+"/"+project.getUser().getUserId()))
                .andExpect(status().isOk());

        Like like = likeRepository.findAll().get(0);

        assertNotNull(like);
        assertNotNull(like.getUsers().getUserId());
        assertNotNull(like.getProject().getId());


    }
    private Project addRecipe() {
        Users users = userRepository.findById(2L).get();
        Project recipe = Project.builder()
                .nickname("호호")
                .user(users)
                .build();

        Project save = projectRepository.save(recipe);

        return save;
    }
}



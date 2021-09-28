package com.make.projects.service;

import com.make.projects.model.domain.Like;
import com.make.projects.model.domain.Project;
import com.make.projects.model.domain.Users;
import com.make.projects.repository.datajpa.like.LikeRepository;
import com.make.projects.repository.datajpa.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/*https://github.com/cocodori/recipe.com/blob/main/src/test/java/com/recipe/like/LikeApiControllerTest.java*/
@Transactional
@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final ProjectRepository projectRepository;

    public boolean addLike(Users users, Long projectId) {
        Project project = projectRepository.findById(projectId).get();

        //중복방지
        if(isNotAlreadyLike(users , project)) {
            likeRepository.save(new Like(project , users));
            return true; //처음 좋아요누름
        }
        return false; //이미 좋아요했었음
    }


    /*
     *   1. 좋아요를 count할 대상 recipe를 가져온다.
     *   2. 가져온 recipe로 like테이블에 쿼리한 결과를 List에 담는다.
     *   3. 현재 로그인한 사용자가
     *       이미 해당 레시피에 좋아요를 눌렀는지 검사하고 결과를 List에 담아 반환한다.
     * */
    public List<String> count(Long projectId, Users loginUser) throws Exception {
        Project project = projectRepository.findById(projectId).orElseThrow();

        Integer projectLikeCount = likeRepository.countByProject(project).orElse(0);
        project.setLikeCount(projectLikeCount);
        List<String> resultData =
                new ArrayList<>(Arrays.asList(String.valueOf(projectLikeCount)));

        //null이 아닐경우 true 리턴
        if (Objects.nonNull(loginUser)) {
            resultData.add(String.valueOf(isNotAlreadyLike(loginUser, project)));
            return resultData;
        }
        return resultData;
    }

    public void cancelLike(Users users , Long projectId){
        Project project = projectRepository.findById(projectId).orElseThrow();
        Like like = likeRepository.findByUsersAndProject(users, project).orElseThrow();
        likeRepository.delete(like);
    }

    //사용자가 이미 좋아요 한 게시물인지 체크
    private boolean isNotAlreadyLike(Users users, Project project){
        return likeRepository.findByUsersAndProject(users, project).isEmpty();
    }
}

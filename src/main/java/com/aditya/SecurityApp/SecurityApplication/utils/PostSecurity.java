package com.aditya.SecurityApp.SecurityApplication.utils;


import com.aditya.SecurityApp.SecurityApplication.dto.PostDTO;
import com.aditya.SecurityApp.SecurityApplication.entities.User;
import com.aditya.SecurityApp.SecurityApplication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/*
 * @author adityagupta
 * @date 22/06/25
 */

@Service
@RequiredArgsConstructor
public class PostSecurity {

    private final PostService postService;

    public boolean isOwnerOfPost(Long postId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO postDTO = postService.getPostById(postId);

        return postDTO.getAuthor().getId().equals(user.getId());
    }
}


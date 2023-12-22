package com.example.app.Service;


import com.example.app.DTO.PostDTO;
import com.example.app.Model.Comment;
import com.example.app.Model.Post;
import com.example.app.Model.User;
import com.example.app.Repository.PostsRepository;
import com.example.app.Repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class PostService {

    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;

    public PostService(PostsRepository postsRepository, UsersRepository usersRepository) {
        this.postsRepository = postsRepository;
        this.usersRepository = usersRepository;
    }


    public boolean setPostImage(Long postId,  String fileName) throws IOException {

        Post post = postsRepository.findPostById(postId);
        if(post==null)
            return false;

        post.setFileName(fileName);
        postsRepository.save(post);
        return true;
    }

    public Long addPost(PostDTO postDTO){

        String username = postDTO.getUsername();
        User user = usersRepository.findByUsername(username);

        if(user == null)
            return 0L;

        Post post = Post.builder().user(user).caption(postDTO.getCaption())
                .build();
        postsRepository.save(post);
        user.addPost(post);
        usersRepository.save(user);

        return post.getId();
    }


}

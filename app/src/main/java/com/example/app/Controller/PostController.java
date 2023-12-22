package com.example.app.Controller;

import com.example.app.DTO.PhotoDTO;
import com.example.app.DTO.PostDTO;
import com.example.app.Service.ImageService;
import com.example.app.Service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping
    public ResponseEntity<Long> addPost(@RequestBody PostDTO postDTO) throws IOException {

        Long postId = postService.addPost(postDTO);
        if(postId==0L) {
            return new ResponseEntity<>(0L, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(postId,HttpStatus.CREATED);
        }

    }


}

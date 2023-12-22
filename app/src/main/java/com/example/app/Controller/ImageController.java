package com.example.app.Controller;

import com.example.app.DTO.PhotoDTO;
import com.example.app.Service.ImageService;
import com.example.app.Service.PostService;
import com.example.app.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

@Controller
@RequestMapping("/image")
public class ImageController {
    private final UserService userService;
    private final ImageService imageService;
    private final PostService postService;

    public ImageController(UserService userService, ImageService imageService, PostService postService) {
        this.userService = userService;
        this.imageService = imageService;
        this.postService = postService;
    }

    @GetMapping("/uploadImage")
    public String displayUploadForm() {
        return "index";
    }


    @PostMapping("/uploadProfileImage/{username}")
    public ResponseEntity<?> uploadProfileImage(@RequestParam("image") MultipartFile file,
                                                    @PathVariable(value = "username") String username) throws IOException {

        PhotoDTO photoDTO = imageService.uploadImage(file);
        if(userService.setProfileImage(username, photoDTO.getFileName()))
            return new ResponseEntity<>("Profile picture uploaded", HttpStatus.OK);

        return new ResponseEntity<>("Profile picture didn't upload", HttpStatus.BAD_REQUEST);

    }

    @PostMapping()
    public ResponseEntity<?> uploadPostImage(@RequestParam("image") MultipartFile file,
                                                @PathVariable(value = "username") Long postId) throws IOException {

        PhotoDTO photoDTO = imageService.uploadImage(file);
        if(!postService.setPostImage(postId, photoDTO.getFileName()))
            return new ResponseEntity<>("Upload failed",HttpStatus.FORBIDDEN);

        return new ResponseEntity<>("Image uploaded",HttpStatus.OK);

    }



}

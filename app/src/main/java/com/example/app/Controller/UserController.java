package com.example.app.Controller;

import com.example.app.DTO.UserProfileDTO;
import com.example.app.Service.ImageService;
import com.example.app.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/user")
@RestController
public class UserController {

    public final ImageService imageService;
    public final UserService userService;


    public UserController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @GetMapping("/profile-image/{username}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable(value = "username") String username) throws IOException {

        byte[] imageBytes = userService.getProfileImage(username);
        if (imageBytes.length == 0) {
            byte[] bytes = new byte[0];
            return new ResponseEntity<>(bytes, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

//    @GetMapping("/getProfileInfo")
//    public ResponseEntity<UserProfileDTO> getUserProfileInfo(){
//
//
//        return new ResponseEntity<UserProfileDTO>();
//    }


}




package com.example.app.Service;

import com.example.app.DTO.UserProfileDTO;
import com.example.app.Repository.UsersRepository;
import com.example.app.Model.User;
import com.example.app.Util.EmailDetails;
import com.example.app.Util.EmailSender;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
public class UserService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\src\\main\\java\\com\\example\\app\\Uploads\\";


    public UserService(UsersRepository userRepository, PasswordEncoder passwordEncoder, EmailSender emailSender){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
    }

    public boolean changePassword(String email){

        User user = userRepository.findByEmail(email);
        String newPassword = UUID.randomUUID().toString();

        EmailDetails emailDetails = new EmailDetails(email,"New password:"+newPassword,"Password change");

        if(emailSender.sendMail(emailDetails)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }

        return false;
    }

    public boolean setProfileImage(String username,  String fileName) {

        User user = userRepository.findByUsername(username);

        if(user==null)
            return false;

        user.setProfileImage(fileName);
        userRepository.save(user);
        return true;
    }


    public byte[] getProfileImage(String username){

        byte[] imageBytes = new byte[0];
        User user = userRepository.findByUsername(username);

        if(user==null)
            return imageBytes;

        String imageName = user.getProfileImage();
        Path path = Path.of(UPLOAD_DIRECTORY + imageName);

        try {
            imageBytes = Files.readAllBytes(path);
        }catch (IOException e){
            System.out.println(e);
        }

        return imageBytes;
    }

    public UserProfileDTO getProfileInfo(String username){

        User user = userRepository.findByUsername(username);
        if(user==null)
            return null;

//        UserProfileDTO userProfileDTO = UserProfileDTO.builder().username(user.getUsername())
//                                    .followerCount(user.getFollowerCount())
//                                        .followingCount(user.getFollowingCount())
//                                            .postsCount(user.getPostCount());



    }






}

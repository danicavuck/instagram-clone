package com.example.app.Service;

import com.example.app.Repository.UserRepository;
import com.example.app.DTO.RegisterDTO;
import com.example.app.Model.User;
import com.example.app.Util.EmailDetails;
import com.example.app.Util.EmailSender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Data
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;



    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailSender emailSender){
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
















}

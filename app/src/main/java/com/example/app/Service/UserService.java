package com.example.app.Service;

import com.example.app.Repository.UserRepository;
import com.example.app.DTO.RegisterDTO;
import com.example.app.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(){}
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> registerUser(RegisterDTO registerDTO){

        if(userRepository.existsByUsername(registerDTO.getUsername()))
            return new ResponseEntity<>("User with that username already exists!", HttpStatus.BAD_REQUEST);

        if(userRepository.existsByEmail(registerDTO.getUsername())){
            return new ResponseEntity<>("User with that email already exists!", HttpStatus.BAD_REQUEST);
        }

        User user = User.builder().username(registerDTO.getPassword())
                                    .password(passwordEncoder.encode(registerDTO.getPassword()))
                                        .email(registerDTO
                                                .getEmail()).build();

        userRepository.save(user);

        return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
    };
















}

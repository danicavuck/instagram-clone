package com.example.app.Security;

import com.example.app.DTO.RegisterDTO;
import com.example.app.Model.User;
import com.example.app.Repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    public MyUserDetailsService(UsersRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByUsername(username);

        if(user==null){
            return UserInfoDetails.builder().build();
        }


        return UserInfoDetails.builder().name(user.getUsername()).password(user.getPassword()).build();

    }

    public String register(RegisterDTO registerDTO) {

        if(!Objects.equals(registerDTO.getPassword(), registerDTO.getRepeatedPassword()))
            return "VALIDATION_ERROR";

        if (registerDTO.getUsername().contains(" ") || !registerDTO.getPassword().matches(PASSWORD_REGEX))
            return "VALIDATION_ERROR";

        if(!userRepository.existsByEmail(registerDTO.getEmail()) && !userRepository.existsByUsername(registerDTO.getUsername())) {
            User user = User.builder().email(registerDTO.getEmail()).username(registerDTO.getUsername()).build();
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            userRepository.save(user);
            return  "SUCCESSES";
        }
    return "FAILED";

    }





}
package com.example.app.Controller;

import com.example.app.DTO.LoginDTO;
import com.example.app.DTO.RegisterDTO;
import com.example.app.Model.User;
import com.example.app.Security.CustomAuthManager;
import com.example.app.Security.JwtUtil;
import com.example.app.Security.MyUserDetailsService;
import com.example.app.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;



@RestController
@RequestMapping("/app")
@CrossOrigin
public class AuthController {

    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomAuthManager customAuthManager;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService,
                                            JwtUtil jwtUtil,CustomAuthManager customAuthManager){

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.myUserDetailsService = userDetailsService;
        this.customAuthManager = customAuthManager;

    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){

        return switch (myUserDetailsService.register(registerDTO)) {
            case "VALIDATION_ERROR" -> new ResponseEntity<>("Passwords don't match", HttpStatus.BAD_REQUEST);
            case "FAILED" -> new ResponseEntity<>("User with that credentials already exists!", HttpStatus.BAD_REQUEST);
            case "SUCCESSES" -> new ResponseEntity<>("User is registered successfully", HttpStatus.OK);
            default -> new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        };
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated()) {
            String token =  jwtUtil.generateToken(authentication.getName());
            return new ResponseEntity<>(token, HttpStatus.OK);

        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }

    }







}

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

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final MyUserDetailsService myUserDetailsService;
    private final UserService userService;

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, UserService userService,
                          JwtUtil jwtUtil){

        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.myUserDetailsService = userDetailsService;


    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){

        return switch (myUserDetailsService.register(registerDTO)) {
            case "VALIDATION_ERROR" -> new ResponseEntity<>("Data doesn't pass validation", HttpStatus.BAD_REQUEST);
            case "FAILED" -> new ResponseEntity<>("User with that credentials already exists", HttpStatus.BAD_REQUEST);
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
            throw new UsernameNotFoundException("invalid user request ");
        }

    }

    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable String email){
        if (!userService.changePassword(email))
            return new ResponseEntity<>("Password change failed",HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("Password is changed", HttpStatus.OK);
    }







}

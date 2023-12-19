package com.example.app.Controller;

import com.example.app.DTO.LoginDTO;
import com.example.app.DTO.RegisterDTO;
import com.example.app.Model.User;
import com.example.app.Security.JwtUtil;
import com.example.app.Security.MyUserDetailsService;
import com.example.app.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@AllArgsConstructor
@RestController
@RequestMapping("/app")
@CrossOrigin
public class AuthController {
   private UserService userService;

    private MyUserDetailsService myUserDetailsService;

    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    // handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){
        return myUserDetailsService.register(registerDTO);
    }


//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
//        return myUserDetailsService.login(loginDTO);
//
//    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody LoginDTO authRequest) {
        var a = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(a);
        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }




}

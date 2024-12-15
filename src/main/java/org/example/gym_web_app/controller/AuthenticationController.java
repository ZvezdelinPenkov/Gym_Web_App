package org.example.gym_web_app.controller;

import org.example.gym_web_app.dto.AuthenticationRequest;
import org.example.gym_web_app.dto.AuthenticationResponse;
import org.example.gym_web_app.dto.UsersDTO;
import org.example.gym_web_app.security.JwtUtil;
import org.example.gym_web_app.service.CustomUserDetailsService;
import org.example.gym_web_app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UsersService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsersDTO usersDTO) {
        userService.register(usersDTO);
        return ResponseEntity.ok("User registered successfully");
    }
}

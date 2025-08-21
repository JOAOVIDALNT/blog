package com.joaovidal.blog.controllers;

import com.joaovidal.blog.configs.auth.JwtUtil;
import com.joaovidal.blog.models.User;
import com.joaovidal.blog.models.dtos.LoginResponse;
import com.joaovidal.blog.models.dtos.LoginUserDto;
import com.joaovidal.blog.models.dtos.RegisterUserDto;
import com.joaovidal.blog.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationService authService;

    public AuthController(JwtUtil jwtUtil, AuthenticationService authService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto request) {
        User created = authService.signup(request);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto request) {
        UserDetails user = authService.authenticate(request);
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }

}

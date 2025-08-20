package com.joaovidal.blog.controllers;

import com.joaovidal.blog.services.JwtService;
import com.joaovidal.blog.models.User;
import com.joaovidal.blog.models.dtos.LoginResponse;
import com.joaovidal.blog.models.dtos.LoginUserDto;
import com.joaovidal.blog.models.dtos.RegisterUserDto;
import com.joaovidal.blog.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto request) {
        User created = authenticationService.signup(request);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> register(@RequestBody LoginUserDto request) {
        User authenticated = authenticationService.authenticate(request);
        String token = jwtService.generateToken(authenticated);
        LoginResponse loginResponse = new LoginResponse(token, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

}

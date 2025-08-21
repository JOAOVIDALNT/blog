package com.joaovidal.blog.controllers;

import com.joaovidal.blog.configs.auth.JwtUtil;
import com.joaovidal.blog.models.dtos.PromoteRequest;
import com.joaovidal.blog.models.dtos.UserInfoResponse;
import com.joaovidal.blog.services.UserService;
import com.joaovidal.blog.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> authenticatedUser(@AuthenticationPrincipal UserDetails userDetails) {
        var email = userDetails.getUsername();
        var userInfo = userService.retrieveUserInfo(email);
        return ResponseEntity.ok(userInfo);
    }
    @GetMapping("/list")
    public ResponseEntity<List<UserInfoResponse>> allUsers() {
        var users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    @PostMapping("/admin/promote")
    public ResponseEntity<UserInfoResponse> promote(@RequestBody PromoteRequest request) {
        var info = userService.promote(request.email());

        return ResponseEntity.ok(info);
    }
}

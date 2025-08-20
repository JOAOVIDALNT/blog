package com.joaovidal.blog.controllers;

import com.joaovidal.blog.services.UserService;
import com.joaovidal.blog.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity authenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        var user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(user);
    }
    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        var users = userService.allUsers();

        return ResponseEntity.ok(users);
    }
}

package com.joaovidal.blog.controllers;

import com.joaovidal.blog.models.Post;
import com.joaovidal.blog.models.dtos.CreatePostRequest;
import com.joaovidal.blog.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> CreatePost(@RequestBody CreatePostRequest post, @AuthenticationPrincipal UserDetails userDetails) {

        var email = userDetails.getUsername();
        var user = postService.create(post, email);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> GetAll() {
        var list = postService.getAll();

        return ResponseEntity.ok(list);
    }

}

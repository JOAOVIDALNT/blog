package com.joaovidal.blog.services;

import com.joaovidal.blog.models.Post;
import com.joaovidal.blog.models.dtos.CreatePostRequest;
import com.joaovidal.blog.repositories.PostRepository;
import com.joaovidal.blog.repositories.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @CacheEvict(cacheNames = "posts", allEntries = true)
    public Post create(CreatePostRequest request, String authorEmail) {

        var author = userRepository.findByEmail(authorEmail).orElseThrow();

        var entity = Post.builder()
                .id(UUID.randomUUID())
                .title(request.title())
                .content(request.content())
                .authorId(author.getId())
                .build();

        postRepository.save(entity);

        return entity;
    }

    @Cacheable(cacheNames = "posts", key = "'all'")
    public List<Post> getAll() {
        return postRepository.findAll();
    }



}

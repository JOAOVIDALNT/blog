package com.joaovidal.blog.repositories;

import com.joaovidal.blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends MongoRepository<Post, UUID> {
}

package com.joaovidal.blog.models.dtos;

public record RegisterRequest(String email, String password, String firstName, String lastName) {}

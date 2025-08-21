package com.joaovidal.blog.models.dtos;

import java.util.List;

public record UserInfoResponse(String email, List<String> roles) {}

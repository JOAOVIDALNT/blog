package com.joaovidal.blog.services;

import com.joaovidal.blog.models.User;
import com.joaovidal.blog.models.dtos.LoginRequest;
import com.joaovidal.blog.models.dtos.RegisterRequest;
import com.joaovidal.blog.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(RegisterRequest input) {
        User user = User.builder()
                .firstName(input.firstName())
                .lastName(input.lastName())
                .email(input.email())
                .password(passwordEncoder.encode(input.password()))
                .roles(new HashSet<>(Set.of("USER")))
                .build();
        return userRepository.save(user);
    }

    public UserDetails authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );
        return userService.loadUserByUsername(input.email());
    }

}

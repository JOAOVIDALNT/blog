package com.joaovidal.blog.services;

import com.joaovidal.blog.models.User;
import com.joaovidal.blog.models.dtos.LoginUserDto;
import com.joaovidal.blog.models.dtos.RegisterUserDto;
import com.joaovidal.blog.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(RegisterUserDto input) {
        User user = User.builder()
                .firstName(input.firstName())
                .lastName(input.lastName())
                .email(input.email())
                .password(passwordEncoder.encode(input.password()))
                .build();
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );

        return userRepository.findByEmail(input.email()).orElseThrow();
    }

}

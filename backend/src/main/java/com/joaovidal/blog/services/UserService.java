package com.joaovidal.blog.services;

import com.joaovidal.blog.models.User;
import com.joaovidal.blog.models.dtos.UserInfoResponse;
import com.joaovidal.blog.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserInfoResponse> allUsers() {
        return userRepository.findAll().stream()
                .map(u -> new UserInfoResponse(u.getEmail(), u.getRoles().stream().toList())).toList();
    }

    public UserInfoResponse retrieveUserInfo(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        return new UserInfoResponse(user.getEmail(), user.getRoles().stream().toList());
    }

    public UserInfoResponse promote(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        if (user.getRoles().stream().anyMatch(r -> r.equals("ADMIN"))) {
            return new UserInfoResponse(user.getEmail(), user.getRoles().stream().toList());
        }

        user.getRoles().add("ADMIN");

        userRepository.save(user);
        return new UserInfoResponse(user.getEmail(), user.getRoles().stream().toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                        .toList()
        );
    }
}

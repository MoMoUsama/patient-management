package com.pm.authservice.service;

import com.pm.authservice.DTO.LoginRerquestDTO;
import com.pm.authservice.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private  final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService,  PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<String> AuthService(LoginRerquestDTO  loginRerquestDTO) {

        // findByEmail(): returns empty Optional if the user not in the DB
        //  and the chain ends and go to the retuen
        Optional<String> token = userService
                .findByEmail(loginRerquestDTO.getEmail())
                .filter(u -> passwordEncoder.matches(LoginRequestDTO.getPassword(), u.getPassword()))
                .map(u -> jwtUtil.generateTpken(u.getEmail(), u.getRole()));

        return token;
    }

}

package com.pm.authservice.controller;

import com.pm.authservice.DTO.LoginRerquestDTO;
import com.pm.authservice.DTO.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/login")
public class AuthController {

    @Operation(summary = "Generate Token on user login")
    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRerquestDTO loginRequest) {

        //authenticate(): may return nothing or String
        Optional<String> tokenOptional = authService.authenticate(LoginRerquestDTO);
        if(tokenOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenOptional.get();
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}

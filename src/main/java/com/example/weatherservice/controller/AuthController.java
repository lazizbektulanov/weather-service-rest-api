package com.example.weatherservice.controller;

import com.example.weatherservice.dto.LoginDto;
import com.example.weatherservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody LoginDto user) {
        return authService.login(user);
    }

    @PostMapping("/register")
    public void register(@RequestBody LoginDto loginDto) {
        authService.register(loginDto);
    }
}

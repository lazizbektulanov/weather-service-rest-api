package com.example.weatherservice.controller;


import com.example.weatherservice.dto.UserDetailsDto;
import com.example.weatherservice.dto.UserDto;
import com.example.weatherservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public Flux<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/details")
    public Flux<UserDetailsDto> getUserDetails() {
        return userService.getUserDetails();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/edit")
    public Mono<ResponseEntity<UserDto>> editUser(@Valid @RequestBody UserDto userDto, @PathVariable("id") Long userId) {
        return userService.editUser(userDto, userId);
    }
}

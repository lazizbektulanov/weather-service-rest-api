package com.example.weatherservice.service;


import com.example.weatherservice.dto.LoginDto;
import com.example.weatherservice.model.User;
import com.example.weatherservice.model.enums.Role;
import com.example.weatherservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final JWTService jwtService;

    private final PasswordEncoder passwordEncoder;

    public Mono<ResponseEntity<?>> login(LoginDto loginDto) {
        Mono<User> foundUser = userRepository.findByEmail(loginDto.getEmail()).defaultIfEmpty(new User());
        return foundUser.map(
                user -> {
                    if (user.getUsername() == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                    }
                    if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                        return ResponseEntity.ok().body(jwtService.generateJwtToken(user.getUsername(), user.getRole()));
                    }
                    return ResponseEntity.badRequest().body("Invalid credentials");
                }
        );
    }

    public void register(LoginDto loginDto) {
        userRepository.save(new User(
                        loginDto.getEmail(),
                        passwordEncoder.encode(loginDto.getPassword()),
                        true,
                        Role.ROLE_USER))
                .subscribe();
    }
}

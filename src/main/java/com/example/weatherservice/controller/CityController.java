package com.example.weatherservice.controller;


import com.example.weatherservice.dto.CityDto;
import com.example.weatherservice.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/city")
public class CityController {

    private final CityService cityService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/list")
    public Flux<CityDto> getAllCities(@AuthenticationPrincipal UsernamePasswordAuthenticationToken token) {
        return cityService.getAllCities(token.getAuthorities());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{cityId}/edit")
    public Mono<ResponseEntity<CityDto>> editCity(@Valid @RequestBody CityDto cityDto, @PathVariable("cityId") Long cityId) {
        return cityService.editCity(cityDto, cityId);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/{cityId}/subscribe")
    public Mono<ResponseEntity<String>> subscribeToCity(@PathVariable("cityId") Long cityId,
                                                        @AuthenticationPrincipal UsernamePasswordAuthenticationToken token) {
        return cityService.subscribeToCity(cityId, token.getPrincipal().toString());
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/subscriptions")
    public Flux<CityDto> getSubscriptions(@AuthenticationPrincipal UsernamePasswordAuthenticationToken token) {
        return cityService.getSubscriptions(token.getPrincipal().toString());
    }
}

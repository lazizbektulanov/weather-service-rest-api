package com.example.weatherservice.controller;

import com.example.weatherservice.dto.WeatherDto;
import com.example.weatherservice.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{cityId}/edit")
    public Mono<ResponseEntity<WeatherDto>> editCityWeather(@Valid @RequestBody WeatherDto weatherDto,
                                                            @PathVariable("cityId") Long cityId) {
        return weatherService.editCityWeather(weatherDto, cityId);
    }
}

package com.example.weatherservice.service;

import com.example.weatherservice.dto.WeatherDto;
import com.example.weatherservice.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;

    public Mono<ResponseEntity<WeatherDto>> editCityWeather(WeatherDto weatherDto, Long cityId) {
        return weatherRepository.findWeatherByCityId(cityId)
                .flatMap(weather -> {
                    weather.setTemperature(weatherDto.getTemperature());
                    weather.setWindDirection(weatherDto.getWindDirection() != null ? weatherDto.getWindDirection() : weather.getWindDirection());
                    weather.setWindSpeed(weatherDto.getWindSpeed() != null ? weatherDto.getWindSpeed() : weather.getWindSpeed());
                    return weatherRepository.save(weather);
                })
                .map(updatedWeather -> ResponseEntity.ok(new WeatherDto(
                        updatedWeather.getId(),
                        updatedWeather.getTemperature(),
                        updatedWeather.getWindSpeed(),
                        updatedWeather.getWindDirection(),
                        updatedWeather.getCityId())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}

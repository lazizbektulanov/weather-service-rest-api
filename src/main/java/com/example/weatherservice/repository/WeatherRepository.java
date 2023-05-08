package com.example.weatherservice.repository;

import com.example.weatherservice.model.Weather;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface WeatherRepository extends ReactiveCrudRepository<Weather, Long> {

    Mono<Weather> findWeatherByCityId(Long cityId);
}

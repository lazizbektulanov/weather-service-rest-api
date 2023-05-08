package com.example.weatherservice.repository;

import com.example.weatherservice.model.City;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CityRepository extends ReactiveCrudRepository<City, Long> {

    Mono<City> findByIdAndEnabled(Long id, boolean enabled);

    Flux<City> findByEnabled(boolean enabled);
}

package com.example.weatherservice.repository;

import com.example.weatherservice.model.Subscription;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SubscriptionRepository extends ReactiveCrudRepository<Subscription, Long> {

    Flux<Subscription> findByUserId(Long userId);

    Mono<Boolean> existsByCityIdAndUserId(Long cityId, Long userId);
}

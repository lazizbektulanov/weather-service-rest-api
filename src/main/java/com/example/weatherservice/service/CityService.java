package com.example.weatherservice.service;

import com.example.weatherservice.dto.CityDto;
import com.example.weatherservice.dto.WeatherDto;
import com.example.weatherservice.model.City;
import com.example.weatherservice.model.Subscription;
import com.example.weatherservice.model.User;
import com.example.weatherservice.model.Weather;
import com.example.weatherservice.model.enums.Role;
import com.example.weatherservice.repository.CityRepository;
import com.example.weatherservice.repository.SubscriptionRepository;
import com.example.weatherservice.repository.UserRepository;
import com.example.weatherservice.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    private final WeatherRepository weatherRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final UserRepository userRepository;

    public Flux<CityDto> getAllCities(Collection<GrantedAuthority> authorities) {
        boolean isAdmin = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ROLE_ADMIN.name()));
        return isAdmin ? cityRepository.findAll().flatMap(this::mapCityWithWeather) :
                cityRepository.findByEnabled(true).flatMap(this::mapCityWithWeather);
    }

    public Mono<ResponseEntity<CityDto>> editCity(CityDto cityDto, Long cityId) {
        return cityRepository.findById(cityId)
                .flatMap(city -> {
                    city.setName(cityDto.getName());
                    city.setEnabled(cityDto.getEnabled());
                    return cityRepository.save(city);
                })
                .map(city -> ResponseEntity.ok(new CityDto(city.getId(), city.getName(), city.isEnabled())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    public Mono<ResponseEntity<String>> subscribeToCity(Long cityId, String username) {
        Mono<User> userMono = userRepository.findByEmail(username);
        return userMono.flatMap(user -> {
            Long userId = user.getId();
            return cityRepository.findById(cityId)
                    .flatMap(city -> subscriptionRepository.existsByCityIdAndUserId(cityId, userId)
                            .flatMap(exists -> {
                                if (exists) {
                                    return Mono.just(ResponseEntity.badRequest().body("Already subscribed to the city"));
                                } else {
                                    Subscription subscription = new Subscription();
                                    subscription.setCityId(cityId);
                                    subscription.setUserId(userId);
                                    return subscriptionRepository.save(subscription)
                                            .map(sub -> ResponseEntity.ok("Successfully subscribed to the city"));
                                }
                            }))
                    .switchIfEmpty(Mono.just(ResponseEntity.badRequest().body("City not found")));
        });
    }

    public Flux<CityDto> getSubscriptions(String username) {
        Mono<User> userMono = userRepository.findByEmail(username);
        return userMono.flatMapMany(user -> {
            Long userId = user.getId();
            return user.getRole().equals(Role.ROLE_ADMIN) ?
                    subscriptionRepository.findByUserId(userId)
                            .flatMap(subscription -> cityRepository.findById(subscription.getCityId()))
                            .flatMap(this::mapCityWithWeather) :
                    subscriptionRepository.findByUserId(userId)
                            .flatMap(subscription -> cityRepository.findByIdAndEnabled(subscription.getCityId(), true))
                            .flatMap(this::mapCityWithWeather);
        });
    }

    private Mono<CityDto> mapCityWithWeather(City city) {
        Mono<Weather> weatherMono = weatherRepository.findWeatherByCityId(city.getId());
        return weatherMono.map(weather -> {
            CityDto cityDto = new CityDto();
            cityDto.setId(city.getId());
            cityDto.setName(city.getName());
            cityDto.setEnabled(city.isEnabled());
            cityDto.setWeatherDto(new WeatherDto(
                    weather.getId(),
                    weather.getTemperature(),
                    weather.getWindSpeed(),
                    weather.getWindDirection(),
                    weather.getCityId()
            ));
            return cityDto;
        });
    }
}

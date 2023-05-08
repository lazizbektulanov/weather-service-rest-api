package com.example.weatherservice.service;


import com.example.weatherservice.dto.CityDto;
import com.example.weatherservice.dto.UserDetailsDto;
import com.example.weatherservice.dto.UserDto;
import com.example.weatherservice.model.enums.Role;
import com.example.weatherservice.repository.CityRepository;
import com.example.weatherservice.repository.SubscriptionRepository;
import com.example.weatherservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final CityRepository cityRepository;

    private final SubscriptionRepository subscriptionRepository;

    public Flux<UserDto> getAllUsers() {
        return userRepository.findAll()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setEmail(user.getEmail());
                    userDto.setEnabled(user.isEnabled());
                    userDto.setRole(user.getRole().name());
                    return userDto;
                });
    }

    public Flux<UserDetailsDto> getUserDetails() {
        return userRepository.findAll()
                .flatMap(user -> subscriptionRepository.findByUserId(user.getId())
                        .flatMap(subscription -> cityRepository.findById(subscription.getCityId()))
                        .map(city -> new CityDto(city.getId(), city.getName()))
                        .collectList()
                        .map(subscribedCities -> {
                            UserDetailsDto userDetailsDto = new UserDetailsDto();
                            userDetailsDto.setUserId(user.getId());
                            userDetailsDto.setUserEmail(user.getEmail());
                            userDetailsDto.setUserSubscribedCities(subscribedCities);
                            return userDetailsDto;
                        }));
    }

    public Mono<ResponseEntity<UserDto>> editUser(UserDto userDto, Long userId) {
        return userRepository.findById(userId)
                .flatMap(user -> {
                    user.setEnabled(userDto.getEnabled());
                    user.setRole(Role.valueOf(userDto.getRole()));
                    return userRepository.save(user);
                })
                .map(user -> ResponseEntity.ok(new UserDto(user.getId(), user.getEmail(), user.isEnabled(), user.getRole().name())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}

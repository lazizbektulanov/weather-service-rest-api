package com.example.weatherservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDetailsDto {

    private Long userId;

    private String userEmail;

    private List<CityDto> userSubscribedCities;
}

package com.example.weatherservice.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WeatherDto {

    private Long id;

    @NotNull
    private Double temperature;

    private Double windSpeed;

    private Integer windDirection;

    private Long cityId;

    public WeatherDto(Double temperature, Double windSpeed, Integer windDirection, Long cityId) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.cityId = cityId;
    }

    public WeatherDto(Long id, Double temperature, Double windSpeed, Integer windDirection) {
        this.id = id;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }

    public WeatherDto() {
    }

    public WeatherDto(Long id, Double temperature, Double windSpeed, Integer windDirection, Long cityId) {
        this.id = id;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.cityId = cityId;
    }


}

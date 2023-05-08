package com.example.weatherservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CityDto {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Boolean enabled;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private WeatherDto weatherDto;

    public CityDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CityDto(Long id, String name, boolean enabled) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
    }
}

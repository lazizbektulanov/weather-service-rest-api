package com.example.weatherservice.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Data
@Table
public class Weather {

    @Id
    private Long id;

    @Column
    private Double temperature;

    @Column(value = "wind_speed")
    private Double windSpeed;

    @Column(value = "wind_direction")
    private Integer windDirection;

    @LastModifiedDate
    @Column(value = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(value = "city_id")
    private Long cityId;

}

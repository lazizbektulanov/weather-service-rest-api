package com.example.weatherservice.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Table(name = "cities")
public class City {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private boolean enabled;
}

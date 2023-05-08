package com.example.weatherservice.model;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(name = "subscriptions")
public class Subscription {

    @Id
    private Long id;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "city_id")
    private Long cityId;

    @CreatedDate
    @Column(value = "created_date")
    private LocalDateTime createdDate;
}

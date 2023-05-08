package com.example.weatherservice.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserDto {

    private Long id;

    private String email;

    @NotNull
    private Boolean enabled;

    @NotNull
    @Pattern(regexp = "ROLE_ADMIN|ROLE_USER")
    private String role;

    public UserDto(Long id, String email, boolean enabled, String role) {
        this.id = id;
        this.email = email;
        this.enabled = enabled;
        this.role = role;
    }

    public UserDto() {
    }
}

package org.wasim.restaurantservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class RestaurantResponseDto {
    private String id;
    private String name;
    private String description;
    private String cuisineType;
    private String city;
    private boolean approved;
    private Double rating;
    private LocalDateTime createdAt;
}

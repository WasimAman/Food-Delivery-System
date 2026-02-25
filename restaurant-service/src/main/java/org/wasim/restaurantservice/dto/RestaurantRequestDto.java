package org.wasim.restaurantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantRequestDto {
    private String name;
    private String description;
    private String cuisineType;
    private String city;
}

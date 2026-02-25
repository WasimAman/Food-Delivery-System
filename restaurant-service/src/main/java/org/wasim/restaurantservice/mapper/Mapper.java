package org.wasim.restaurantservice.mapper;

import org.wasim.restaurantservice.dto.RestaurantRequestDto;
import org.wasim.restaurantservice.dto.RestaurantResponseDto;
import org.wasim.restaurantservice.dto.RestaurantSummaryDto;
import org.wasim.restaurantservice.entity.Restaurant;

import java.time.LocalDateTime;

public class Mapper {
    public static Restaurant toRestaurant(String userId, RestaurantRequestDto requestDto) {
        return Restaurant.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .cuisineType(requestDto.getCuisineType())
                .city(requestDto.getCity())
                .approved(false)
                .ownerId(userId)
                .rating(0.0)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static RestaurantResponseDto toRestaurantResponseDto(Restaurant restaurant) {
        return RestaurantResponseDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .description(restaurant.getDescription())
                .cuisineType(restaurant.getCuisineType())
                .city(restaurant.getCity())
                .approved(restaurant.isApproved())
                .rating(restaurant.getRating())
                .createdAt(restaurant.getCreatedAt())
                .build();
    }

    public static RestaurantSummaryDto toRestaurantSummaryDto(Restaurant restaurant) {
        RestaurantSummaryDto summaryDto = new RestaurantSummaryDto();
        summaryDto.setId(restaurant.getId());
        summaryDto.setName(restaurant.getName());
        summaryDto.setCity(restaurant.getCity());
        return summaryDto;
    }
}

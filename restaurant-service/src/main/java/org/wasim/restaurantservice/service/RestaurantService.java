package org.wasim.restaurantservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.wasim.restaurantservice.dto.ApiResponse;
import org.wasim.restaurantservice.dto.RestaurantRequestDto;
import org.wasim.restaurantservice.dto.RestaurantResponseDto;
import org.wasim.restaurantservice.dto.RestaurantSummaryDto;
import org.wasim.restaurantservice.entity.Restaurant;
import org.wasim.restaurantservice.exception.RestaurantNotFoundException;
import org.wasim.restaurantservice.mapper.Mapper;
import org.wasim.restaurantservice.repository.RestaurantRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public ApiResponse<RestaurantResponseDto> createRestaurant(String userId, RestaurantRequestDto requestDto) {
        Restaurant restaurant = Mapper.toRestaurant(userId,requestDto);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return new ApiResponse<>(
                true,
                "Restaurant registered successfully!",
                Mapper.toRestaurantResponseDto(savedRestaurant),
                LocalDateTime.now()
        );
    }

    public ApiResponse<RestaurantResponseDto> updateRestaurant(String restaurantId, RestaurantRequestDto requestDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new RestaurantNotFoundException("Restaurant not found with id: "+restaurantId));
        restaurant.setName(requestDto.getName());
        restaurant.setDescription(requestDto.getDescription());
        restaurant.setCity(requestDto.getCity());
        restaurant.setCuisineType(requestDto.getCuisineType());

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return new ApiResponse<>(
                true,
                "Restaurant Updated successfully!",
                Mapper.toRestaurantResponseDto(updatedRestaurant),
                LocalDateTime.now()
        );
    }

    public ApiResponse<List<RestaurantResponseDto>> getAllRestaurants() {
        List<RestaurantResponseDto> response = restaurantRepository.findAll()
                .stream()
                .map(restaurant -> Mapper.toRestaurantResponseDto(restaurant))
                .toList();

        return new ApiResponse<List<RestaurantResponseDto>>(
                true,
                "All restaurant fetched successfully!",
                response,
                LocalDateTime.now()
        );
    }

    public ApiResponse<RestaurantResponseDto> getRestaurantById(String restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new RestaurantNotFoundException("Restaurant not found with id: "+restaurantId));

        return new ApiResponse<>(
                true,
                "Restaurant fetched successfully!",
                Mapper.toRestaurantResponseDto(restaurant),
                LocalDateTime.now()
        );
    }

    public ApiResponse<RestaurantResponseDto> approveRestaurant(String restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new RestaurantNotFoundException("Restaurant not found with id: "+restaurantId));
        restaurant.setApproved(true);
        restaurantRepository.save(restaurant);

        return new ApiResponse<>(
                true,
                "Restaurant approved successfully!",
                Mapper.toRestaurantResponseDto(restaurant),
                LocalDateTime.now()
        );
    }

    public RestaurantSummaryDto getRestaurantSummary(String restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new RestaurantNotFoundException("Restaurant not found with id: "+restaurantId));

        return Mapper.toRestaurantSummaryDto(restaurant);
    }
}

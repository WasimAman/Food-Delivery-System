package org.wasim.restaurantservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.wasim.restaurantservice.dto.ApiResponse;
import org.wasim.restaurantservice.dto.RestaurantRequestDto;
import org.wasim.restaurantservice.dto.RestaurantResponseDto;
import org.wasim.restaurantservice.dto.RestaurantSummaryDto;
import org.wasim.restaurantservice.service.RestaurantService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/restaurants")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestBody RestaurantRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getPrincipal().toString();
        ApiResponse<RestaurantResponseDto> response = restaurantService.createRestaurant(userId,requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(@PathVariable("id") String restaurantId,@RequestBody RestaurantRequestDto requestDto) {
        ApiResponse<RestaurantResponseDto> response = restaurantService.updateRestaurant(restaurantId,requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllRestaurants() {
        ApiResponse<List<RestaurantResponseDto>> response = restaurantService.getAllRestaurants();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable("id") String restaurantId) {
        ApiResponse<RestaurantResponseDto> response = restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approvedRestaurant(@PathVariable("id") String restaurantId) {
        ApiResponse<RestaurantResponseDto> response = restaurantService.approveRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    /*
        THESE METHOD WILL BE CALLED BY OTHER SERVICE PROBABLY ORDER-SERVICE
     */
    @GetMapping("/{id}/summary")
    RestaurantSummaryDto getRestaurantSummary(@PathVariable("id") String restaurantId){
        return restaurantService.getRestaurantSummary(restaurantId);
    }
}

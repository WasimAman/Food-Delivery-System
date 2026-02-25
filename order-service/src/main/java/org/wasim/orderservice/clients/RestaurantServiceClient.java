package org.wasim.orderservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.wasim.orderservice.config.FeignConfig;
import org.wasim.orderservice.dto.response.RestaurantSummaryDto;

@FeignClient(name = "RESTAURANT-SERVICE",configuration = FeignConfig.class)
public interface RestaurantServiceClient {

    @GetMapping("/api/restaurants/{id}/summary")
    RestaurantSummaryDto getRestaurantSummary(@PathVariable("id") String restaurantId);
}
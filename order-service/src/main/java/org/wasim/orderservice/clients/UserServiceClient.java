package org.wasim.orderservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.wasim.orderservice.config.FeignConfig;
import org.wasim.orderservice.dto.response.UserSummaryDto;

@FeignClient(name = "USER-SERVICE",configuration = FeignConfig.class)
public interface UserServiceClient {

    @GetMapping("/api/users/{id}/summary")
    UserSummaryDto getUserSummary(@PathVariable("id") String userId);
}
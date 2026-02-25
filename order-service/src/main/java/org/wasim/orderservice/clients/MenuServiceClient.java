package org.wasim.orderservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.wasim.orderservice.config.FeignConfig;
import org.wasim.orderservice.dto.response.MenuItemSummaryDto;

import java.util.List;

@FeignClient(
        name = "MENU-SERVICE",
        configuration = FeignConfig.class
)
public interface MenuServiceClient {

    @GetMapping("/api/menus/{id}/summary")
    MenuItemSummaryDto getMenuItem(@PathVariable("id") String menuItemId);

    @PostMapping("/api/menus/bulk-summary")
    List<MenuItemSummaryDto> getMenuItemsBulk(@RequestBody List<String> ids);
}
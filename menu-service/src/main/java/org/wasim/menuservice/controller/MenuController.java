package org.wasim.menuservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wasim.menuservice.dto.*;
import org.wasim.menuservice.service.MenuService;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@AllArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<?> createMenu(@RequestBody MenuRequestDto requestDto){
        ApiResponse<MenuItemResponseDto> response = menuService.createMenu(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMenu(
            @PathVariable("id") String menuId,
            @RequestBody MenuUpdateRequestDto requestDto
    ){
        ApiResponse<MenuItemResponseDto> response = menuService.updateMenu(menuId,requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<?> getRestaurantMenus(@PathVariable String restaurantId){
        ApiResponse<List<MenuItemResponseDto>> response = menuService.getRestaurantMenus(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<?> updateAvailability(@PathVariable("id") String menuId,@RequestParam boolean availability){
        ApiResponse<MenuItemResponseDto> response = menuService.updateAvailability(menuId,availability);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*
        THESE METHOD WILL BE CALLED BY OTHER SERVICE PROBABLY ORDER-SERVICE
     */

    @GetMapping("/{id}/summary")
    public MenuItemSummaryDto getMenuItem(@PathVariable("id") String menuItemId){
        return menuService.getMenuItem(menuItemId);
    }


    @PostMapping("/bulk-summary")
    List<MenuItemSummaryDto> getMenuItemsBulk(@RequestBody List<String> ids){
        return menuService.getMenuItemsBulk(ids);
    }
}

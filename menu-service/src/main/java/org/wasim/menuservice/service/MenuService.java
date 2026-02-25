package org.wasim.menuservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.wasim.menuservice.dto.*;
import org.wasim.menuservice.entity.MenuItem;
import org.wasim.menuservice.exception.ManuItemNotFoundException;
import org.wasim.menuservice.mapper.Mapper;
import org.wasim.menuservice.repository.MenuRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public ApiResponse<MenuItemResponseDto> createMenu(MenuRequestDto requestDto) {
        MenuItem menuItem = Mapper.toMenuItem(requestDto);
        MenuItem savedMenu = menuRepository.save(menuItem);
        return new ApiResponse<>(
                true,
                "Menu item is created successfully!",
                Mapper.toMenuItemResponseDto(savedMenu),
                LocalDateTime.now()
        );
    }

    public ApiResponse<MenuItemResponseDto> updateMenu(String menuId, MenuUpdateRequestDto requestDto) {
        MenuItem menuItem = menuRepository.findById(menuId)
                .orElseThrow(()-> new ManuItemNotFoundException("Menu item is not found with id: "+menuId));
        Mapper.toMenuItem(menuItem,requestDto);
        MenuItem savedMenuItem = menuRepository.save(menuItem);
        return new ApiResponse<>(
                true,
                "Menu item is updated successfully!",
                Mapper.toMenuItemResponseDto(savedMenuItem),
                LocalDateTime.now()
        );
    }

    public ApiResponse<List<MenuItemResponseDto>> getRestaurantMenus(String restaurantId) {
        List<MenuItem> menuItems = menuRepository.findByRestaurantId(restaurantId);
        return new ApiResponse<>(
                true,
                "Menu items fetched of a restaurant",
                Mapper.toMenuItemResponseDto(menuItems),
                LocalDateTime.now()
        );
    }

    public ApiResponse<MenuItemResponseDto> updateAvailability(String menuId,boolean availability) {
        MenuItem menuItem = menuRepository.findById(menuId)
                .orElseThrow(()-> new ManuItemNotFoundException("Menu item is not found with id: "+menuId));
        menuItem.setAvailable(availability);
        MenuItem savaedMenuItem = menuRepository.save(menuItem);
        return new ApiResponse<>(
                true,
                "Availability updated successfully!",
                Mapper.toMenuItemResponseDto(savaedMenuItem),
                LocalDateTime.now()
        );
    }

    public MenuItemSummaryDto getMenuItem(String menuItemId) {
        MenuItem menuItem = menuRepository.findById(menuItemId)
                .orElseThrow(()-> new ManuItemNotFoundException("Menu item is not found with id: "+menuItemId));
        return Mapper.toMenuItemSummaryDto(menuItem);
    }

    public List<MenuItemSummaryDto> getMenuItemsBulk(List<String> ids) {
        List<MenuItemSummaryDto> summaryDtos = new ArrayList<>();
        for(String menuItemId : ids){
            MenuItem menuItem = menuRepository.findById(menuItemId)
                    .orElseThrow(()-> new ManuItemNotFoundException("Menu item is not found with id: "+menuItemId));
            summaryDtos.add(Mapper.toMenuItemSummaryDto(menuItem));
        }

        return summaryDtos;
    }
}

package org.wasim.menuservice.mapper;

import org.wasim.menuservice.dto.MenuItemResponseDto;
import org.wasim.menuservice.dto.MenuItemSummaryDto;
import org.wasim.menuservice.dto.MenuRequestDto;
import org.wasim.menuservice.dto.MenuUpdateRequestDto;
import org.wasim.menuservice.entity.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static MenuItem toMenuItem(MenuRequestDto requestDto) {
        return MenuItem.builder()
                .restaurantId(requestDto.getRestaurantId())
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .veg(requestDto.isVeg())
                .available(requestDto.isAvailable())
                .category(requestDto.getCategory())
                .build();
    }

    public static void toMenuItem(MenuItem menuItem, MenuUpdateRequestDto requestDto) {
        menuItem.setName(requestDto.getName());
        menuItem.setDescription(requestDto.getDescription());
        menuItem.setPrice(requestDto.getPrice());
        menuItem.setAvailable(requestDto.isAvailable());
        menuItem.setCategory(requestDto.getCategory());
        menuItem.setVeg(requestDto.isVeg());
    }

    public static MenuItemResponseDto toMenuItemResponseDto(MenuItem menuItem) {
        return MenuItemResponseDto.builder()
                .id(menuItem.getId())
                .restaurantId(menuItem.getRestaurantId())
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice())
                .veg(menuItem.isVeg())
                .available(menuItem.isAvailable())
                .category(menuItem.getCategory())
                .build();
    }

    public static List<MenuItemResponseDto> toMenuItemResponseDto(List<MenuItem> menuItems) {
        List<MenuItemResponseDto> menuItemResponseDtos = new ArrayList<>();
        for(MenuItem menuItem : menuItems){
            MenuItemResponseDto menuItemResponseDto = toMenuItemResponseDto(menuItem);
            menuItemResponseDtos.add(menuItemResponseDto);
        }

        return menuItemResponseDtos;
    }

    public static MenuItemSummaryDto toMenuItemSummaryDto(MenuItem menuItem) {
        MenuItemSummaryDto menuItemSummaryDto = new MenuItemSummaryDto();
        menuItemSummaryDto.setId(menuItem.getId());
        menuItemSummaryDto.setName(menuItem.getName());

        return menuItemSummaryDto;
    }
}

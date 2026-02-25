package org.wasim.menuservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemResponseDto {

    private String id;
    private String restaurantId;
    private String name;
    private String description;
    private Double price;
    private boolean available;
    private boolean veg;
    private String category;
}
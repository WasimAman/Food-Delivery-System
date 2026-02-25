package org.wasim.menuservice.dto;

import lombok.Data;

@Data
public class MenuRequestDto {
    private String restaurantId;
    private String name;
    private String description;
    private Double price;
    private boolean available;
    private boolean veg;
    private String category;
}

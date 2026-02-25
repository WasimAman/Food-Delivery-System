package org.wasim.orderservice.dto.response;

import lombok.Data;

@Data
public class UserSummaryDto {

    private String userId;
    private String name;
    private String email;
}
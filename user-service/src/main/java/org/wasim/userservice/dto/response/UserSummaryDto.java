package org.wasim.userservice.dto.response;


import lombok.Data;

@Data
public class UserSummaryDto {

    private String id;
    private String name;
    private String email;
}
package org.wasim.userservice.mapper;

import org.wasim.userservice.dto.request.AddressRequestDto;
import org.wasim.userservice.dto.response.AddressResponseDto;
import org.wasim.userservice.dto.response.LoginResponseDto;
import org.wasim.userservice.dto.response.UserResponseDto;
import org.wasim.userservice.dto.request.UserSignupRequestDto;
import org.wasim.userservice.dto.response.UserSummaryDto;
import org.wasim.userservice.entity.Address;
import org.wasim.userservice.entity.Role;
import org.wasim.userservice.entity.User;
import org.wasim.userservice.exception.InvalidRoleException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static User toUserEntity(UserSignupRequestDto requestDto) {

        Role role;
        try {
            role = Role.valueOf(requestDto.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidRoleException("Invalid role provided");
        }

        return User.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .role(role)
                .enabled(true)
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .addresses(new ArrayList<>())
                .build();
    }

    public static UserResponseDto toUserDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .enabled(user.isEnabled())
                .deleted(user.isDeleted())
                .createdAt(user.getCreatedAt())
                .build();
    }


    public static Address toAddress(User user, AddressRequestDto addressRequestDto) {
        return Address.builder()
                .street(addressRequestDto.getStreet())
                .city(addressRequestDto.getCity())
                .state(addressRequestDto.getState())
                .pincode(addressRequestDto.getPincode())
                .user(user)
                .build();
    }

    public static UserResponseDto toUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .enabled(user.isEnabled())
                .deleted(user.isDeleted())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static LoginResponseDto toLoginResponseDto(User user, String token) {
        return LoginResponseDto.builder()
                .accessToken(token)
                .user(toUserResponseDto(user))
                .build();
    }

    public static AddressResponseDto toAddressResponseDto(Address address) {
        return AddressResponseDto.builder()
                .id(address.getId())
                .city(address.getCity())
                .state(address.getState())
                .street(address.getStreet())
                .pincode(address.getPincode())
                .build();
    }

    public static UserSummaryDto toUserSummaryDto(User user) {
        UserSummaryDto userSummaryDto = new UserSummaryDto();
        userSummaryDto.setId(user.getId());
        userSummaryDto.setEmail(user.getEmail());
        userSummaryDto.setName(user.getName());

        System.out.println(userSummaryDto);
        return userSummaryDto;
    }
}

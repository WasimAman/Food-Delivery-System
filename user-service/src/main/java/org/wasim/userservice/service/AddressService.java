package org.wasim.userservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.wasim.userservice.dto.request.AddressRequestDto;
import org.wasim.userservice.dto.response.AddressResponseDto;
import org.wasim.userservice.dto.response.ApiResponse;
import org.wasim.userservice.entity.Address;
import org.wasim.userservice.entity.User;
import org.wasim.userservice.exception.UserNotFoundException;
import org.wasim.userservice.mapper.Mapper;
import org.wasim.userservice.repository.AddressRepository;
import org.wasim.userservice.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public ApiResponse<AddressResponseDto> createAddress(String userId, AddressRequestDto addressRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found with id: " + userId));
        Address address = Mapper.toAddress(user,addressRequestDto);
        Address savedAddress = addressRepository.save(address);
        return new ApiResponse<>(
                true,
                "Address saved successfully",
                Mapper.toAddressResponseDto(savedAddress),
                LocalDateTime.now()
        );
    }
}

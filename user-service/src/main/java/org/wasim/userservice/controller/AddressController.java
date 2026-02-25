package org.wasim.userservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.wasim.userservice.dto.request.AddressRequestDto;
import org.wasim.userservice.dto.response.AddressResponseDto;
import org.wasim.userservice.dto.response.ApiResponse;
import org.wasim.userservice.service.AddressService;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/address")
    public ResponseEntity<ApiResponse<AddressResponseDto>> createAddress(@Valid @RequestBody AddressRequestDto addressRequestDto){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getPrincipal().toString();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        ApiResponse<AddressResponseDto> response = addressService.createAddress(userId,addressRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

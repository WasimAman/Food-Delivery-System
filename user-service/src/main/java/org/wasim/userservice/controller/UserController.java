package org.wasim.userservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.wasim.userservice.dto.request.UserDto;
import org.wasim.userservice.dto.request.UserLoginRequestDto;
import org.wasim.userservice.dto.request.UserSignupRequestDto;
import org.wasim.userservice.dto.response.*;
import org.wasim.userservice.service.UserService;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(@Valid @RequestBody UserSignupRequestDto requestDto){
        ApiResponse<UserResponseDto> response = userService.registerUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody UserLoginRequestDto requestDto){
        ApiResponse<LoginResponseDto> response = userService.login(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponseDto>> getProfile() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getPrincipal().toString();

        ApiResponse<UserResponseDto> response = userService.findUserProfileById(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateProfile(@Valid @RequestBody UserDto userDto) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getPrincipal().toString();
        ApiResponse<UserResponseDto> response = userService.updateUser(userId,userDto);
        return ResponseEntity.ok(response);
    }

    /*
        OTHER SERVICES WILL HIT BLOW API'S
     */

    @GetMapping("/{id}/summary")
    UserSummaryDto getUserSummary(@PathVariable("id") String userId) {
        return userService.getUserSummary(userId);
    }
}

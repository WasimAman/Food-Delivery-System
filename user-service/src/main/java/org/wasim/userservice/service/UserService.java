package org.wasim.userservice.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.wasim.userservice.dto.request.UserDto;
import org.wasim.userservice.dto.request.UserLoginRequestDto;
import org.wasim.userservice.dto.request.UserSignupRequestDto;
import org.wasim.userservice.dto.response.*;
import org.wasim.userservice.entity.User;
import org.wasim.userservice.exception.UserAlreadyExistsException;
import org.wasim.userservice.exception.UserNotFoundException;
import org.wasim.userservice.mapper.Mapper;
import org.wasim.userservice.repository.UserRepository;
import org.wasim.userservice.security.JwtService;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public ApiResponse<UserResponseDto> registerUser(UserSignupRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new UserAlreadyExistsException("User already exists by email: "+requestDto.getEmail());
        }

        User user = Mapper.toUserEntity(requestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return new ApiResponse<UserResponseDto>(
                true,
                "User registered successfully",
                Mapper.toUserResponseDto(savedUser),
                LocalDateTime.now()
        );
    }

    public ApiResponse<LoginResponseDto> login(UserLoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(),requestDto.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository
                .findByEmail(userDetails.getUsername())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
        String token = jwtService.generateAccessToken(user);
        return new ApiResponse<>(
                true,
                "Login successful",
                Mapper.toLoginResponseDto(user,token),
                LocalDateTime.now()
        );
    }

    public ApiResponse<UserResponseDto> findUserProfileById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found with id: " + userId));
        return new ApiResponse<>(
                true,
                "User fetched successfully!",
                Mapper.toUserResponseDto(user),
                LocalDateTime.now()
        );
    }

    public ApiResponse<UserResponseDto> updateUser(String userId, UserDto userDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: "+userId));


        if (!user.getEmail().equals(userDto.getEmail())
                && userRepository.existsByEmail(userDto.getEmail())) {

            throw new UserAlreadyExistsException("User already exists with email: "+userDto.getEmail());
        }

        // Direct update
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        if(userDto.getPassword() != null){
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        User updatedUser = userRepository.save(user);

        return new ApiResponse<>(
                true,
                "User updated successfully",
                Mapper.toUserResponseDto(updatedUser),
                LocalDateTime.now()
        );
    }

    public UserSummaryDto getUserSummary(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: "+userId));

        return Mapper.toUserSummaryDto(user);
    }
}

package project.carsharing.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.carsharing.dto.user.UserDto;
import project.carsharing.dto.user.UserLoginRequestDto;
import project.carsharing.dto.user.UserLoginResponseDto;
import project.carsharing.dto.user.UserRegistrationDto;
import project.carsharing.security.AuthenticationService;
import project.carsharing.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid UserRegistrationDto requestDto) {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}

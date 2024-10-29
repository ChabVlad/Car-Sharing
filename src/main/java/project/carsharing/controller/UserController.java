package project.carsharing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.carsharing.dto.user.UserDto;
import project.carsharing.dto.user.UserRequestDto;
import project.carsharing.dto.user.UserUpdateRoleDto;
import project.carsharing.model.User;
import project.carsharing.service.UserService;

@RestController("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("{id}/role ")
    public UserDto updateRole(@PathVariable Long id, @RequestBody UserUpdateRoleDto requestDto) {
        return userService.updateRole(id, requestDto);
    }

    @GetMapping("/me")
    public UserDto getCurrentUser(
            Authentication authentication
    ) {
        return userService.findById(getCurrentUserId(authentication));
    }

    @PatchMapping("/me")
    public UserDto updateCurrentUser(
            @RequestBody UserRequestDto requestDto,
            Authentication authentication
    ) {
        return userService.update(getCurrentUserId(authentication), requestDto);
    }

    private Long getCurrentUserId(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}

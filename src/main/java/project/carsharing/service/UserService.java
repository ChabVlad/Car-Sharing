package project.carsharing.service;

import project.carsharing.dto.user.UserDto;
import project.carsharing.dto.user.UserRegistrationDto;
import project.carsharing.dto.user.UserUpdateRequestDto;
import project.carsharing.dto.user.UserUpdateRoleDto;

public interface UserService {

    UserDto findById(Long id);

    UserDto updateRole(Long id, UserUpdateRoleDto requestDto);

    UserDto update(Long id, UserUpdateRequestDto requestDto);

    UserDto register(UserRegistrationDto requestDto);
}

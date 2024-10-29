package project.carsharing.service;

import project.carsharing.dto.user.UserDto;
import project.carsharing.dto.user.UserRegistrationDto;
import project.carsharing.dto.user.UserRequestDto;
import project.carsharing.dto.user.UserUpdateRoleDto;

public interface UserService {

    UserDto findById(Long id);

    UserDto updateRole(Long id, UserUpdateRoleDto requestDto);

    UserDto update(Long id, UserRequestDto requestDto);

    UserDto register(UserRegistrationDto requestDto);
}

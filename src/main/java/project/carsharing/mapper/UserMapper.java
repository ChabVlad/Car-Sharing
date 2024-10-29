package project.carsharing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import project.carsharing.config.MapperConfig;
import project.carsharing.dto.user.UserDto;
import project.carsharing.dto.user.UserRegistrationDto;
import project.carsharing.dto.user.UserRequestDto;
import project.carsharing.dto.user.UserUpdateRoleDto;
import project.carsharing.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRequestDto requestDto);

    User toModel(UserRegistrationDto requestDto);

    UserDto toDto(User user);

    void updateRole(UserUpdateRoleDto requestDto, @MappingTarget User user);

    void updateUser(UserRequestDto requestDto, @MappingTarget User user);

}

package project.carsharing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import project.carsharing.config.MapperConfig;
import project.carsharing.dto.user.UserDto;
import project.carsharing.dto.user.UserRegistrationDto;
import project.carsharing.dto.user.UserUpdateRequestDto;
import project.carsharing.dto.user.UserUpdateRoleDto;
import project.carsharing.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserUpdateRequestDto requestDto);

    User toModel(UserRegistrationDto requestDto);

    UserDto toDto(User user);

    void updateRole(UserUpdateRoleDto requestDto, @MappingTarget User user);

    void updateUser(UserUpdateRequestDto requestDto, @MappingTarget User user);

}

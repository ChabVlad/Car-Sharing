package project.carsharing.dto.user;

import project.carsharing.model.Role;

public record UserDto(
        String email,
        String firstName,
        String lastName,
        Role role
) {
}

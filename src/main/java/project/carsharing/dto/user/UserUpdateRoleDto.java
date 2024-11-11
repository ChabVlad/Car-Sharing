package project.carsharing.dto.user;

import lombok.Getter;
import lombok.Setter;
import project.carsharing.model.Role;

@Getter
@Setter
public class UserUpdateRoleDto {
    private Role role;
}

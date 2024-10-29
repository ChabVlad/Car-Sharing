package project.carsharing.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import project.carsharing.validation.FieldMatch;

@Getter
@Setter
@FieldMatch(
        first = "password",
        second = "repeatPassword",
        message = "The password fields must match"
)
public class UserRegistrationDto {
    @Email
    private String email;
    @Min(6)
    @Max(16)
    private String password;
    @Min(6)
    @Max(16)
    private String repeatPassword;
}

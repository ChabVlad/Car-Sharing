package project.carsharing.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UserUpdateRequestDto {
    private String firstName;
    private String lastName;
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 16, message = "Password must be between 6 and 16 characters")
    private String password;
    @NotBlank
    @Size(min = 6, max = 16, message = "Repeat password must be between 6 and 16 characters")
    private String repeatPassword;
}

package project.carsharing.dto.rental;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalReturnDateDto {
    @NotNull
    private LocalDate actualReturnDate;
}

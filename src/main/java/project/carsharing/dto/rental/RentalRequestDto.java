package project.carsharing.dto.rental;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalRequestDto {
    @NotNull
    private LocalDate rentalDate;
    @NotNull
    private LocalDate returnDate;
    @NotNull
    private Long carId;
    @NotNull
    private Long userId;
}

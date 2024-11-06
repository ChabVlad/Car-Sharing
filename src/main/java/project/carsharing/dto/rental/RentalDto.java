package project.carsharing.dto.rental;

import java.time.LocalDate;

public record RentalDto(
        LocalDate rentalDate,
        LocalDate returnDate,
        LocalDate actualReturnDate,
        Long carId,
        Long userId
) {
}

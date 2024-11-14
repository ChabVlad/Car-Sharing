package project.carsharing.dto.rental;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalRequestDto {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;
    @NotNull
    private Long carId;
}

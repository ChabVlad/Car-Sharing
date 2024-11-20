package project.carsharing.dto.car;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import project.carsharing.model.CarType;

import java.math.BigDecimal;

@Getter
@Setter
public class CarRequestDto {
    @NotBlank
    private String model;
    @NotBlank
    private String brand;
    @NotNull
    private CarType type;
    @Positive
    private int inventory;
    @DecimalMin("0")
    private double dailyFee;
}

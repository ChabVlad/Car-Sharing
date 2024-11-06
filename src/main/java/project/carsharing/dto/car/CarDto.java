package project.carsharing.dto.car;

import project.carsharing.model.CarType;

public record CarDto(
        String model,
        String brand,
        CarType type,
        double dailyFee
) {
}
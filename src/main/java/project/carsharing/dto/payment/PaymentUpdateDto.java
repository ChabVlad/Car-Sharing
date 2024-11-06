package project.carsharing.dto.payment;

import jakarta.validation.constraints.NotNull;
import project.carsharing.model.PaymentStatus;

public class PaymentUpdateDto {
    @NotNull
    private PaymentStatus status;
}

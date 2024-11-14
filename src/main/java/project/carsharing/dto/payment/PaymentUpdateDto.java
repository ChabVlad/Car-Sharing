package project.carsharing.dto.payment;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import project.carsharing.model.PaymentStatus;

@Getter
@Setter
public class PaymentUpdateDto {
    @NotNull
    private PaymentStatus status;
}

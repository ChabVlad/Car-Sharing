package project.carsharing.dto.payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.net.URL;
import lombok.Getter;
import lombok.Setter;
import project.carsharing.model.PaymentStatus;
import project.carsharing.model.PaymentType;

@Getter
@Setter
public class PaymentRequestDto {
    private PaymentStatus status = PaymentStatus.PENDING;
    @NotNull
    private PaymentType type;
    @NotNull
    private Long rentalId;
    private URL sessionUrl;
    @NotNull
    private String sessionId;
    @Positive
    private Long amountToPay;
}

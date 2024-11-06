package project.carsharing.dto.payment;

import java.net.URL;
import project.carsharing.model.PaymentStatus;
import project.carsharing.model.PaymentType;

public record PaymentDto(
        PaymentStatus status,
        PaymentType type,
        Long rentalId,
        URL sessionUrl,
        String sessionId,
        double amountToPay
) {
}

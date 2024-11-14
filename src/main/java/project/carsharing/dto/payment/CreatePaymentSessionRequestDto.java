package project.carsharing.dto.payment;

import project.carsharing.model.PaymentType;

public record CreatePaymentSessionRequestDto(
        Long rentalId,
        PaymentType paymentType
) {
}

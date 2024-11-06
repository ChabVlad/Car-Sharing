package project.carsharing.service;

import java.util.List;
import project.carsharing.dto.payment.CreatePaymentSessionRequestDto;
import project.carsharing.dto.payment.PaymentDto;

public interface PaymentService {
    List<PaymentDto> getPayments(Long userId);

    PaymentDto createPaymentSession(CreatePaymentSessionRequestDto request);

    PaymentDto checkSuccessfulPayment(Long rentalId);

    String cancelPayment(Long rentalId);
}

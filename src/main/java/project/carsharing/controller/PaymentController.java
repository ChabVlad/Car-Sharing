package project.carsharing.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.carsharing.dto.payment.CreatePaymentSessionRequestDto;
import project.carsharing.dto.payment.PaymentDto;
import project.carsharing.service.PaymentService;

@RestController("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/?user_id={userId}")
    public List<PaymentDto> getPayments(@PathVariable Long userId) {
        return paymentService.getPayments(userId);
    }

    @PostMapping
    public PaymentDto createPayment(@RequestBody @Valid CreatePaymentSessionRequestDto requestDto) {
        return paymentService.createPaymentSession(requestDto);
    }

    @GetMapping("/success")
    public PaymentDto succeedPayment(@RequestParam("session_id") Long sessionId) {
        return paymentService.checkSuccessfulPayment(sessionId);
    }

    @GetMapping("/cancel")
    public String canceledPayment(@RequestParam("session_id") Long sessionId) {
        return paymentService.cancelPayment(sessionId);
    }
}

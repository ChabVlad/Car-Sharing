package project.carsharing.service.impl;

import static com.stripe.param.checkout.SessionCreateParams.LineItem;
import static com.stripe.param.checkout.SessionCreateParams.Mode;
import static com.stripe.param.checkout.SessionCreateParams.PaymentMethodOptions.AcssDebit.Currency;
import static com.stripe.param.checkout.SessionCreateParams.PaymentMethodType;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.carsharing.dto.payment.CreatePaymentSessionRequestDto;
import project.carsharing.dto.payment.PaymentDto;
import project.carsharing.exception.PaymentException;
import project.carsharing.mapper.PaymentMapper;
import project.carsharing.model.Payment;
import project.carsharing.model.PaymentStatus;
import project.carsharing.model.PaymentType;
import project.carsharing.model.Rental;
import project.carsharing.repository.payment.PaymentRepository;
import project.carsharing.repository.rental.RentalRepository;
import project.carsharing.service.NotificationService;
import project.carsharing.service.PaymentService;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private static final BigDecimal FINE_MULTIPLIER = BigDecimal.valueOf(1.3);
    private static final int UNIT_AMOUNT_MULTIPLIER = 100;
    private static final Long DEFAULT_CAR_IN_RENTAL_QUANTITY = 1L;
    private static final String PRODUCT_NAME = "Car rental";
    private static final String PRODUCT_DESCRIPTION = "Payment for rental of the car";
    private static final String COMPLETE_SESSION_STATUS = "complete";
    private static final String CANCEL_URL_MESSAGE = "Payment was cancelled";

    private final RentalRepository rentalRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationService notificationService;

    @Value("${stripe.success.url}")
    private String successUrl;

    @Value("${stripe.cancel.url}")
    private String cancelUrl;

    @Override
    public List<PaymentDto> getPayments(Long userId) {
        List<Rental> userRentals = rentalRepository.findAllByUserId(userId);
        List<Payment> userPayments = paymentRepository.findAllByRentalsId(
                userRentals.stream().map(Rental::getId).toList()
        );
        return userPayments.stream().map(paymentMapper::toDto).toList();
    }

    @Transactional
    @Override
    public PaymentDto createPaymentSession(CreatePaymentSessionRequestDto request) {
        checkIfRentalIsPaid(request);
        long moneyToPay = calculateAmount(request);

        Session session = createStripeSession(moneyToPay, request);

        Payment payment = savePayment(session, request, moneyToPay);
        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentDto checkSuccessfulPayment(Long rentalId) {
        Payment payment = paymentRepository.findByRentalId(rentalId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find payment with rental ID: " + rentalId)
                );

        Session session = retrieveStripeSession(payment.getSessionId());

        if (COMPLETE_SESSION_STATUS.equals(session.getStatus())) {
            return handleSuccessfulPayment(payment);
        }

        throw new PaymentException("Payment is not completed");
    }

    @Override
    public String cancelPayment(Long rentalId) {
        return CANCEL_URL_MESSAGE;
    }

    private void checkIfRentalIsPaid(CreatePaymentSessionRequestDto request) {
        Optional<Payment> payment = paymentRepository.findByRentalId(request.rentalId());
        if (payment.isPresent() && PaymentStatus.PAID.equals(payment.get().getStatus())) {
            throw new PaymentException(
                    "Rental with ID: " + request.rentalId() + " is already paid"
            );
        }
    }

    private long calculateAmount(CreatePaymentSessionRequestDto request) {
        Rental rental = findById(request.rentalId());
        BigDecimal dailyFee = rental.getCar().getDailyFee();
        BigDecimal moneyToPay = BigDecimal.valueOf(
                rental.getReturnDate().getDayOfYear() - rental.getRentalDate().getDayOfYear()
                )
                .multiply(dailyFee);

        if (PaymentType.FINE.equals(request.paymentType())) {
            BigDecimal overdueAmount = BigDecimal.valueOf(
                    rental.getActualReturnDate().getDayOfYear()
                            - rental.getReturnDate().getDayOfYear()
                    )
                    .multiply(dailyFee)
                    .multiply(FINE_MULTIPLIER);
            moneyToPay = moneyToPay.add(overdueAmount);
        }

        return moneyToPay.longValue();
    }

    private Session createStripeSession(long moneyToPay, CreatePaymentSessionRequestDto request) {
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(PaymentMethodType.CARD)
                .setMode(Mode.PAYMENT)
                .setSuccessUrl(getSuccessUrl(request.rentalId()))
                .setCancelUrl(getCancelUrl(request.rentalId()))
                .addAllLineItem(Collections.singletonList(
                        LineItem.builder()
                                .setPriceData(LineItem.PriceData.builder()
                                        .setCurrency(Currency.USD.getValue())
                                        .setUnitAmount(moneyToPay * UNIT_AMOUNT_MULTIPLIER)
                                        .setProductData(LineItem.PriceData.ProductData.builder()
                                                .setName(PRODUCT_NAME)
                                                .setDescription(PRODUCT_DESCRIPTION)
                                                .build())
                                        .build())
                                .setQuantity(DEFAULT_CAR_IN_RENTAL_QUANTITY)
                                .build()))
                .build();

        try {
            return Session.create(params);
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create Stripe session", e);
        }
    }

    private Payment savePayment(
            Session session,
            CreatePaymentSessionRequestDto request,
            Long moneyToPay) {
        Payment payment = new Payment();
        payment.setSessionId(session.getId());
        payment.setSessionUrl(session.getUrl());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setType(request.paymentType());
        payment.setRental(findById(request.rentalId()));
        payment.setAmountToPay(BigDecimal.valueOf(moneyToPay));

        return paymentRepository.save(payment);
    }

    private Session retrieveStripeSession(String sessionId) {
        try {
            return Session.retrieve(sessionId);
        } catch (StripeException e) {
            throw new RuntimeException("Failed to retrieve session from Stripe", e);
        }
    }

    private PaymentDto handleSuccessfulPayment(Payment payment) {
        payment.setStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);

        String notificationMessage = buildNotificationMessage(payment);
        notificationService.sendNotification(notificationMessage);

        return paymentMapper.toDto(payment);
    }

    private String buildNotificationMessage(Payment payment) {
        return String.format(
                """
                        Payment Successful!\

                        Amount: $%.2f\

                        Date: %s\

                        Payment ID: %d\

                        Customer ID: %d\

                        Customer Name: %s %s""",
                payment.getAmountToPay(), LocalDateTime.now(), payment.getId(),
                payment.getRental().getUser().getId(),
                payment.getRental().getUser().getFirstName(),
                payment.getRental().getUser().getLastName()
        );
    }

    private String getSuccessUrl(Long rentalId) {
        return successUrl + rentalId;
    }

    private String getCancelUrl(Long rentalId) {
        return cancelUrl + rentalId;
    }

    private Rental findById(Long rentalId) {
        return rentalRepository.findById(rentalId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find rental with ID: " + rentalId)
                );
    }
}

package project.carsharing.util;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripeUtil {
    public static final BigDecimal CURRENCY_MULTIPLIER = new BigDecimal(100);
    public static final Long DEFAULT_QUANTITY = 1L;
    private static final String DEFAULT_CURRENCY = "usd";
    @Value("${stripe.success.url}")
    private String successUrl;
    @Value("${stripe.cancel.url}")
    private String cancelUrl;

    public StripeUtil(@Value("${stripe.secret.key}") String apiKey) {
        Stripe.apiKey = apiKey;
    }

    public Session createStripeSession(BigDecimal amount, String name) throws StripeException {
        SessionCreateParams params = SessionCreateParams.builder()
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .addLineItem(getLineItem(amount, name))
                .build();
        return Session.create(params);
    }

    private SessionCreateParams.LineItem getLineItem(BigDecimal amount, String name) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(DEFAULT_QUANTITY)
                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(DEFAULT_CURRENCY)
                        .setUnitAmountDecimal(convertToCents(amount))
                        .setProductData(SessionCreateParams.LineItem
                                .PriceData.ProductData.builder()
                                .setName(name)
                                .build()
                        )
                        .build()
                )
                .build();
    }

    private BigDecimal convertToCents(BigDecimal amount) {
        return amount.multiply(CURRENCY_MULTIPLIER);
    }

    public Session retrieveSession(String sessionId) throws StripeException {
        return Session.retrieve(sessionId);
    }
}

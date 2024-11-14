package project.carsharing.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @Value("${stripe.secret.key}")
    private String secretKey;

    public void init() {
        if (secretKey != null) {
            Stripe.apiKey = secretKey;
        } else {
            throw new RuntimeException("Stripe secret key not set");
        }
    }
}

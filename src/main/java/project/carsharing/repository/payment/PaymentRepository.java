package project.carsharing.repository.payment;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.carsharing.model.Payment;
import project.carsharing.model.Rental;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Long findUserIdByRental(Rental rental);

    Optional<Payment> findByRentalId(Long rentalId);

    Optional<Payment> findBySessionId(String sessionId);

    List<Payment> findAllByRental_UserId(Long userId);

    List<Payment> findAllByRentalsId(List<Long> list);
}

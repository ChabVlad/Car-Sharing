package project.carsharing.repository.payment;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.carsharing.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findBySessionId(String sessionId);

    @Query("SELECT payment FROM Payment payment "
            + "LEFT JOIN FETCH payment.rental rental "
            + "WHERE rental.id IN :rentalIds")
    List<Payment> findAllByRentalsId(@Param("rentalIds") List<Long> rentalsIds);

    Optional<Payment> findByRentalId(Long rentalId);
}

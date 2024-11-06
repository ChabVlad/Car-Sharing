package project.carsharing.repository.rental;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.carsharing.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findAllByUserIdAndActive(Long userId, Boolean active, Pageable pageable);

    List<Rental> findAllByUserId(Long userId);
}

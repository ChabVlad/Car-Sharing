package project.carsharing.repository.rental;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.carsharing.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("SELECT r FROM Rental r WHERE r.user.id = :userId AND r.isActive = :isActive")
    List<Rental> findAllByUserIdAndActive(
            @Param("userId") Long userId,
            @Param("isActive") boolean isActive,
            Pageable pageable
    );

    List<Rental> findAllByUserId(Long userId);

    Rental findByUserId(Long userId);
}

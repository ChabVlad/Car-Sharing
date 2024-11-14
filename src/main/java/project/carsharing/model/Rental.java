package project.carsharing.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "rentals")
@SQLDelete(sql = "UPDATE rentals SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Getter
@Setter
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rental_date", nullable = false)
    private LocalDate rentalDate;
    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;
    @Column(name = "actual_return_date")
    private LocalDate actualReturnDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "is_active", columnDefinition = "TINYINT(1)")
    private boolean isActive;
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isDeleted;
}

package project.carsharing.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import project.carsharing.dto.rental.RentalDto;
import project.carsharing.dto.rental.RentalRequestDto;
import project.carsharing.dto.rental.RentalReturnDateDto;
import project.carsharing.model.Rental;
import project.carsharing.model.User;

public interface RentalService {
    Rental addRental(RentalRequestDto requestDto, User user);

    List<RentalDto> findAllRentalsByUserId(Long userId, boolean isActive, Pageable pageable);

    RentalDto findById(Long id);

    RentalDto updateActualReturnDate(RentalReturnDateDto requestDto, Long id);
}

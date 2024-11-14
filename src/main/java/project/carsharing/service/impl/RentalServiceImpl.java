package project.carsharing.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.carsharing.dto.rental.RentalDto;
import project.carsharing.dto.rental.RentalRequestDto;
import project.carsharing.dto.rental.RentalReturnDateDto;
import project.carsharing.exception.TryToGetFromEmptyInventoryException;
import project.carsharing.mapper.RentalMapper;
import project.carsharing.model.Car;
import project.carsharing.model.Rental;
import project.carsharing.model.User;
import project.carsharing.repository.car.CarRepository;
import project.carsharing.repository.rental.RentalRepository;
import project.carsharing.service.RentalService;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final CarRepository carRepository;

    @Transactional
    @Override
    public Rental addRental(RentalRequestDto requestDto, User user) {
        Rental rental = rentalMapper.toModel(requestDto);
        rental.setRentalDate(LocalDate.now());
        rental.setUser(user);

        Car car = carRepository
                .findById(requestDto.getCarId()).orElseThrow(EntityNotFoundException::new);
        if (car.getInventory() < 1) {
            throw new TryToGetFromEmptyInventoryException("This car not available for now: " + car);
        }
        rental.setCar(car);
        rental.setActive(true);
        return rentalRepository.save(rental);
    }

    @Override
    public List<RentalDto> findAllRentalsByUserId(
            Long userId,
            boolean isActive,
            Pageable pageable
    ) {
        List<Rental> rentals = rentalRepository
                .findAllByUserIdAndActive(userId, isActive, pageable);
        return rentals.stream()
                .map(rentalMapper::toDto)
                .toList();
    }

    @Override
    public RentalDto findById(Long id) {
        Rental rental = findRentalById(id);
        return rentalMapper.toDto(rental);
    }

    @Transactional
    @Override
    public RentalDto updateActualReturnDate(
            RentalReturnDateDto requestDto,
            Long userId
    ) {
        Rental rental = rentalRepository.findByUserId(userId);
        rental.setActive(false);
        rentalRepository.save(rental);
        rentalMapper.updateModel(requestDto, rental);
        return rentalMapper.toDto(rental);
    }

    private Rental findRentalById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found by id: " + id));
    }
}

package project.carsharing.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.carsharing.dto.rental.RentalDto;
import project.carsharing.dto.rental.RentalRequestDto;
import project.carsharing.dto.rental.RentalReturnDateDto;
import project.carsharing.model.Rental;
import project.carsharing.model.User;
import project.carsharing.service.CarService;
import project.carsharing.service.RentalService;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;
    private final CarService carService;

    @PostMapping
    public Rental addRental(
            @RequestBody RentalRequestDto requestDto,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        carService.decreaseInventory(requestDto.getCarId());
        return rentalService.addRental(requestDto, user);
    }

    @GetMapping
    public List<RentalDto> getRentalsByUserId(
            @RequestParam Long userId,
            @RequestParam boolean isActive,
            Pageable pageable
    ) {
        return rentalService.findAllRentalsByUserId(userId, isActive, pageable);
    }

    @GetMapping("/{id}")
    public RentalDto getRentalById(@PathVariable Long id) {
        return rentalService.findById(id);
    }

    @PostMapping("/return")
    public RentalDto returnRental(
            @RequestBody @Valid RentalReturnDateDto requestDto,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return rentalService.updateActualReturnDate(requestDto, user.getId());
    }
}

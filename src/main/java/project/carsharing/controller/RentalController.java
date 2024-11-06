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
import org.springframework.web.bind.annotation.RestController;
import project.carsharing.dto.rental.RentalDto;
import project.carsharing.dto.rental.RentalRequestDto;
import project.carsharing.dto.rental.RentalReturnDateDto;
import project.carsharing.model.Rental;
import project.carsharing.model.User;
import project.carsharing.service.CarService;
import project.carsharing.service.RentalService;

@RestController("/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;
    private final CarService carService;

    @PostMapping
    public Rental addRental(@RequestBody RentalRequestDto requestDto) {
        carService.decreaseInventory(requestDto.getCarId());
        return rentalService.addRental(requestDto);
    }

    @GetMapping("/?user_id={id}&is_active={isActive}")
    public List<RentalDto> getRentalsByUserId(
            @PathVariable Long id,
            @PathVariable boolean isActive,
            Pageable pageable
    ) {
        return rentalService.findAllRentalsByUserId(id, isActive, pageable);
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

package project.carsharing.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.carsharing.dto.car.CarDto;
import project.carsharing.dto.car.CarRequestDto;
import project.carsharing.dto.car.CarSearchParameters;
import project.carsharing.model.Car;
import project.carsharing.service.CarService;

@RestController("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping
    public Car addCar(
            @RequestBody @Valid CarRequestDto requestDto
    ) {
        return carService.save(requestDto);
    }

    @GetMapping
    public List<CarDto> getAllCars(Pageable pageable) {
        return carService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CarDto getCarById(@PathVariable Long id) {
        return carService.findById(id);
    }

    @PutMapping("/{id}")
    public CarDto updateCar(
            @PathVariable Long id,
            @RequestBody @Valid CarRequestDto requestDto
    ) {
        return carService.update(requestDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteById(id);
    }

    @GetMapping("/search")
    public List<CarDto> searchCar(CarSearchParameters parameters) {
        return carService.search(parameters);
    }
}

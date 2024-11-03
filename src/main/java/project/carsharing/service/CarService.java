package project.carsharing.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import project.carsharing.dto.car.CarDto;
import project.carsharing.dto.car.CarRequestDto;
import project.carsharing.dto.car.CarSearchParameters;
import project.carsharing.model.Car;

public interface CarService {
    Car save(CarRequestDto requestDto);

    List<CarDto> findAll(Pageable pageable);

    CarDto findById(Long id);

    CarDto update(CarRequestDto requestDto, Long id);

    void deleteById(Long id);

    List<CarDto> search(CarSearchParameters searchParameters);

    void increaseInventory(Long id);

    void decreaseInventory(Long id);
}

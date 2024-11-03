package project.carsharing.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.carsharing.dto.car.CarDto;
import project.carsharing.dto.car.CarRequestDto;
import project.carsharing.dto.car.CarSearchParameters;
import project.carsharing.mapper.CarMapper;
import project.carsharing.model.Car;
import project.carsharing.repository.car.CarRepository;
import project.carsharing.repository.car.CarSpecificationBuilder;
import project.carsharing.service.CarService;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;
    private CarMapper carMapper;
    private CarSpecificationBuilder carSpecificationBuilder;

    @Transactional
    @Override
    public Car save(CarRequestDto requestDto) {
        Car car = carMapper.toModel(requestDto);
        return carRepository.save(car);
    }

    @Override
    public List<CarDto> findAll(Pageable pageable) {
        List<Car> cars = (List<Car>) carRepository.findAll(pageable);
        return cars.stream()
                .map(carMapper::toDto)
                .toList();
    }

    @Override
    public CarDto findById(Long id) {
        Car car = findCarById(id);
        return carMapper.toDto(car);
    }

    @Transactional
    @Override
    public CarDto update(CarRequestDto requestDto, Long id) {
        Car car = findCarById(id);
        carMapper.updateCarFromDto(requestDto, car);
        carRepository.save(car);
        return carMapper.toDto(car);
    }

    @Override
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<CarDto> search(CarSearchParameters searchParameters) {
        Specification<Car> carSpecification = carSpecificationBuilder.build(searchParameters);
        return carRepository.findAll(carSpecification)
                .stream()
                .map(carMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void increaseInventory(Long id) {
        Car car = findCarById(id);
        car.setInventory(car.getInventory() + 1);
        carRepository.save(car);
    }

    @Transactional
    @Override
    public void decreaseInventory(Long id) {
        Car car = findCarById(id);
        car.setInventory(car.getInventory() - 1);
        carRepository.save(car);
    }

    private Car findCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found by id: " + id));
    }
}

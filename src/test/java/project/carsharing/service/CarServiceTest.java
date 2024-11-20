package project.carsharing.service;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import project.carsharing.dto.car.CarDto;
import project.carsharing.dto.car.CarRequestDto;
import project.carsharing.mapper.CarMapper;
import project.carsharing.model.Car;
import project.carsharing.model.CarType;
import project.carsharing.repository.car.CarRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    @Mock
    CarMapper carMapper;
    @Mock
    CarRepository carRepository;
    @InjectMocks
    CarService carService;

    @Test
    void save_validRequestDto_returnCar() {
        CarRequestDto requestDto = new CarRequestDto();
        requestDto.setBrand("BMW");
        requestDto.setModel("X3");
        requestDto.setType(CarType.SEDAN);
        requestDto.setInventory(2);
        requestDto.setDailyFee(120);

        Car car = new Car();
        car.setId(1L);
        car.setBrand(requestDto.getBrand());
        car.setModel(requestDto.getModel());
        car.setInventory(requestDto.getInventory());
        car.setType(requestDto.getType());
        car.setDailyFee(requestDto.getDailyFee());

        when(carMapper.toModel(requestDto)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);

        Car actual = carService.save(requestDto);
        Car expected = car;

        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    void findAll_validRequestDto_returnListOfCars() {
        Pageable pageable = PageRequest.of(0, 10);

        Car car = new Car();
        car.setId(1L);
        car.setModel("X3");
        car.setBrand("BMW");
        car.setType(CarType.SEDAN);
        car.setInventory(2);
        car.setDailyFee(120);

        List<Car> carList = List.of(car);
        Page<Car> carPage = new PageImpl<>(carList);

        CarDto expected = new CarDto("X3", "BMW", CarType.SEDAN, 120);

        when(carRepository.findAll(pageable)).thenReturn(carPage);
        when(carMapper.toDto(car)).thenReturn(expected);

        List<CarDto> actual = carService.findAll(pageable);

        assertEquals(expected, actual.get(0));

        verify(carRepository).findAll(pageable);
        verify(carMapper).toDto(car);
    }

    @Test
    void findById_validRequestDto_returnCarDto() {
        Car car = new Car();
        car.setId(1L);
        car.setModel("X3");
        car.setBrand("BMW");
        car.setType(CarType.SEDAN);
        car.setInventory(2);
        car.setDailyFee(120);

        CarDto expected = new CarDto("X3", "BMW", CarType.SEDAN, 120);

        when(carMapper.toDto(car)).thenReturn(expected);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));

        CarDto actual = carService.findById(car.getId());

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}

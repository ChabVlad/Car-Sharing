package project.carsharing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import project.carsharing.config.MapperConfig;
import project.carsharing.dto.car.CarDto;
import project.carsharing.dto.car.CarRequestDto;
import project.carsharing.model.Car;

@Mapper(config = MapperConfig.class)
public interface CarMapper {
    Car toModel(CarRequestDto requestDto);

    CarDto toDto(Car car);

    void updateCarFromDto(CarRequestDto requestDto, @MappingTarget Car car);
}

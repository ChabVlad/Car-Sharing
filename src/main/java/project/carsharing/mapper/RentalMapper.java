package project.carsharing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import project.carsharing.config.MapperConfig;
import project.carsharing.dto.rental.RentalDto;
import project.carsharing.dto.rental.RentalRequestDto;
import project.carsharing.dto.rental.RentalReturnDateDto;
import project.carsharing.model.Rental;

@Mapper(config = MapperConfig.class)
public interface RentalMapper {
    Rental toModel(RentalRequestDto requestDto);

    RentalDto toDto(Rental rental);

    void updateModel(RentalReturnDateDto requestDto, @MappingTarget Rental rental);
}

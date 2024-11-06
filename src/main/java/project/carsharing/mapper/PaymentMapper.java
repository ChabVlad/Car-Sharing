package project.carsharing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import project.carsharing.config.MapperConfig;
import project.carsharing.dto.payment.PaymentDto;
import project.carsharing.dto.payment.PaymentRequestDto;
import project.carsharing.dto.payment.PaymentUpdateDto;
import project.carsharing.model.Payment;

@Mapper(config = MapperConfig.class)
public interface PaymentMapper {
    Payment toModel(PaymentRequestDto requestDto);

    PaymentDto toDto(Payment payment);

    void updateStatus(PaymentUpdateDto requestDto, @MappingTarget Payment payment);
}

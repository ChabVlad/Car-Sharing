package project.carsharing.repository.car;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import project.carsharing.dto.car.CarSearchParameters;
import project.carsharing.model.Car;
import project.carsharing.repository.filter.SpecificationBuilder;
import project.carsharing.repository.filter.SpecificationProviderManager;

@Component
@RequiredArgsConstructor
public class CarSpecificationBuilder implements SpecificationBuilder<Car> {
    private static final String MODEL = "model";
    private static final String BRAND = "brand";
    private static final String TYPE = "type";
    private static final String DAILY_FEE = "dailyFee";
    private final SpecificationProviderManager<Car> specificationProviderManager;

    @Override
    public Specification<Car> build(CarSearchParameters searchParameters) {
        Specification<Car> spec = Specification.where(null);
        if (searchParameters.model() != null && searchParameters.model().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(MODEL)
                    .getSpecification(searchParameters.model()));
        }
        if (searchParameters.brand() != null && searchParameters.brand().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(BRAND)
                    .getSpecification(searchParameters.brand()));
        }
        if (searchParameters.type() != null && searchParameters.type().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(TYPE)
                    .getSpecification(searchParameters.type()));
        }
        if (searchParameters.dailyFee() != null && searchParameters.dailyFee().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(DAILY_FEE)
                    .getSpecification(searchParameters.dailyFee()));
        }
        return spec;
    }
}

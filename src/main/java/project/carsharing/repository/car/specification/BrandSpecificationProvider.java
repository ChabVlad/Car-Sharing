package project.carsharing.repository.car.specification;

import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import project.carsharing.model.Car;
import project.carsharing.repository.filter.SpecificationProvider;

@Component
public class BrandSpecificationProvider implements SpecificationProvider<Car> {
    private static final String BRAND = "brand";

    @Override
    public String getKey() {
        return BRAND;
    }

    @Override
    public Specification<Car> getSpecification(String[] params) {
        return ((root, query, criteriaBuilder)
                -> root.get(BRAND).in(Arrays.stream(params).toArray()));
    }
}

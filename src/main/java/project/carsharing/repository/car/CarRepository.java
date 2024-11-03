package project.carsharing.repository.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import project.carsharing.model.Car;

@Repository
public interface CarRepository extends
        JpaRepository<Car, Long>,
        JpaSpecificationExecutor<Car>,
        PagingAndSortingRepository<Car, Long> {
}

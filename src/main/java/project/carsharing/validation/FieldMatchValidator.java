package project.carsharing.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(
            Object object,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(object);
        Object firstObject = beanWrapper.getPropertyValue(firstFieldName);
        Object secondObject = beanWrapper.getPropertyValue(secondFieldName);

        return Objects.equals(firstObject, secondObject);
    }
}

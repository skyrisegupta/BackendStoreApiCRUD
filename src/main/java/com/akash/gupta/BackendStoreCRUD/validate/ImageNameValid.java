package com.akash.gupta.BackendStoreCRUD.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.*;

@Target({ ElementType.FIELD , ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
// imager validator as we use annotation in the validationa section
//                                                          annotation ka type , vo property kis type ki hai jise aap validate karna chah rahe hai

public @interface ImageNameValid{
//    error message
    String message() default "{jakarta.validation.constraints.NotBlank.message}";

//    represent group of Constraint
    Class<?>[] groups() default {};

//     additional information about annotations
    Class<? extends Payload>[] payload() default {};




}

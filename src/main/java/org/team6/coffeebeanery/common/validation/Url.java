package org.team6.coffeebeanery.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UrlValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Url {
    
    String message() default "유효한 URL이 아닙니다.";

    Class<?>[] groups() default {}; // groups 추가 (필수)

    Class<? extends Payload>[] payload() default {}; // payload 추가 (필수)
}
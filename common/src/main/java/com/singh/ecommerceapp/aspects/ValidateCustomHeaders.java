package com.singh.ecommerceapp.aspects;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateCustomHeaders {
    String[] mandatoryHeaders() default {};
    String[] optionalHeaders() default {};
}

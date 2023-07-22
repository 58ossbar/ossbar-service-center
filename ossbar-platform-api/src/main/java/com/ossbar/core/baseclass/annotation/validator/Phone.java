package com.ossbar.core.baseclass.annotation.validator;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Phone {
    String msg() default "";
}

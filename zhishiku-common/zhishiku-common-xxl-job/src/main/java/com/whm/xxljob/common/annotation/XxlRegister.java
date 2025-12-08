package com.whm.xxljob.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface XxlRegister {
    String cron();

    String jobDesc() default "default jobDesc";

    String author() default "default Author";

    String executorRouteStrategy() default "SHARDING_BROADCAST";

    int triggerStatus() default 0;
}
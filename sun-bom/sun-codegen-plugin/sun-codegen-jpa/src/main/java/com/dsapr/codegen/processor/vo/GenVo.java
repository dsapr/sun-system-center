package com.dsapr.codegen.processor.vo;

import java.lang.annotation.*;

/**
 * @Author: Gim
 * @Description:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GenVo {

    String pkgName();

    String sourcePath() default "src/main/java";

    boolean overrideSource() default false;

    boolean jpa() default true;
}

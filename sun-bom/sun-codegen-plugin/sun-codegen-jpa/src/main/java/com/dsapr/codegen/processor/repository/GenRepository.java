package com.dsapr.codegen.processor.repository;

import java.lang.annotation.*;

/**
 * @author gim
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GenRepository {

  String pkgName();

  String sourcePath() default "src/main/java";

  boolean overrideSource() default false;
}

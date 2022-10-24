package com.dsapr.codegen.processor.controller;

import java.lang.annotation.*;

/**
 * @author gim
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GenController {

  String pkgName();

  String sourcePath() default "src/main/java";

  boolean overrideSource() default false;
}

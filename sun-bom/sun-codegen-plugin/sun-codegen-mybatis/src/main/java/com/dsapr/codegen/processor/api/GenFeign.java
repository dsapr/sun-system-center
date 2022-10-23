package com.dsapr.codegen.processor.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gim
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GenFeign {

  String pkgName();

  String serverName() default "xxxSrv";

  String sourcePath() default "src/main/java";

  boolean overrideSource() default false;
}

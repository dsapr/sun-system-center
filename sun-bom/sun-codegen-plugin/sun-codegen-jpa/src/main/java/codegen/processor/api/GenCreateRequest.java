package codegen.processor.api;

import java.lang.annotation.*;

/**
 * @author gim
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GenCreateRequest {

  String pkgName();

  String sourcePath() default "src/main/java";

  boolean overrideSource() default false;
}

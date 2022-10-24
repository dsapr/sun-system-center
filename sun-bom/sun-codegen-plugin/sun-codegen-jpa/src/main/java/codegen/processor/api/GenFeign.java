package codegen.processor.api;

import java.lang.annotation.*;

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

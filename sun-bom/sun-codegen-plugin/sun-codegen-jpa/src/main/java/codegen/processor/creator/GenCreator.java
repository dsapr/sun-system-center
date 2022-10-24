package codegen.processor.creator;


import java.lang.annotation.*;

/**
 * @Author: Gim
 * @Description:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GenCreator {

  String pkgName();

  String sourcePath() default "src/main/java";

  boolean overrideSource() default false;
}

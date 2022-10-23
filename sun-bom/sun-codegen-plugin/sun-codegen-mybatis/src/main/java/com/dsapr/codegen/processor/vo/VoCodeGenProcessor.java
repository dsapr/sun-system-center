package com.dsapr.codegen.processor.vo;

import com.dsapr.codegen.processor.BaseCodeGenProcessor;
import com.dsapr.codegen.spi.CodeGenProcessor;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Set;

/**
 * @author gim vo 代码生成器
 */
@AutoService(value = CodeGenProcessor.class)
public class VoCodeGenProcessor extends BaseCodeGenProcessor {

  public static final String SUFFIX = "VO";

  @Override
  public Class<? extends Annotation> getAnnotation() {
    return GenVo.class;
  }

  @Override
  public String generatePackage(TypeElement typeElement) {
    return typeElement.getAnnotation(GenVo.class).pkgName();
  }

  @Override
  protected void generateClass(TypeElement typeElement, RoundEnvironment roundEnvironment) {
    Set<VariableElement> fields = findFields(typeElement,
        ve -> Objects.isNull(ve.getAnnotation(IgnoreVo.class)));
    String className = PREFIX + typeElement.getSimpleName() + SUFFIX;
    String sourceClassName = typeElement.getSimpleName() + SUFFIX;
    Builder builder = TypeSpec.classBuilder(className)
        // 继承父类
        .superclass(AbstractBaseJpaVO.class)
        .addModifiers(Modifier.PUBLIC)
        .addAnnotation(Schema.class)
        .addAnnotation(Data.class);
    addSetterAndGetterMethod(builder, fields);
    // 生成构造器
    MethodSpec.Builder constructorSpecBuilder = MethodSpec.constructorBuilder()
        .addParameter(TypeName.get(typeElement.asType()), "source") // 参数类型、名称
        .addModifiers(Modifier.PUBLIC); // 修饰符
    // 方法体
    constructorSpecBuilder.addStatement("super(source)");
    fields.stream().forEach(f -> {
      // 替换占位
      constructorSpecBuilder.addStatement("this.set$L(source.get$L())", getFieldDefaultName(f),
          getFieldDefaultName(f));
    });
    // 默认构造方法
    builder.addMethod(MethodSpec.constructorBuilder()
        .addModifiers(Modifier.PROTECTED)
        .build());
    builder.addMethod(constructorSpecBuilder.build());
    String packageName = generatePackage(typeElement);
    // 生成 java 文件
    genJavaFile(packageName, builder);
    genJavaFile(packageName, getSourceTypeWithConstruct(typeElement,sourceClassName, packageName, className));
  }
}

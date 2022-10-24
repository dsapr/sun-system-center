package com.dsapr.codegen.processor.api;

import com.dsapr.codegen.processor.BaseCodeGenProcessor;
import com.dsapr.codegen.processor.DefaultNameContext;
import com.dsapr.codegen.processor.vo.IgnoreVo;
import com.dsapr.codegen.spi.CodeGenProcessor;
import com.dsapr.common.model.AbstractJpaResponse;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.TypeSpec;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Set;

/**
 * @Author: Gim
 * @Date: 2019-10-08 17:14
 * @Description:
 */
@AutoService(value = CodeGenProcessor.class)
public class GenResponseProcessor extends BaseCodeGenProcessor {

  public static String RESPONSE_SUFFIX = "Response";
  @Override
  protected void generateClass(TypeElement typeElement, RoundEnvironment roundEnvironment) {
    DefaultNameContext nameContext = getNameContext(typeElement);
    Set<VariableElement> fields = findFields(typeElement,
        p -> Objects.isNull(p.getAnnotation(IgnoreVo.class)));
    TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(nameContext.getResponseClassName())
        .addModifiers(Modifier.PUBLIC)
        .superclass(AbstractJpaResponse.class)
        .addAnnotation(Schema.class);
    addSetterAndGetterMethodWithConverter(typeSpecBuilder, fields);
    genJavaSourceFile(generatePackage(typeElement),
        typeElement.getAnnotation(GenResponse.class).sourcePath(), typeSpecBuilder);
  }

  @Override
  public Class<? extends Annotation> getAnnotation() {
    return GenResponse.class;
  }

  @Override
  public String generatePackage(TypeElement typeElement) {
    return typeElement.getAnnotation(GenResponse.class).pkgName();
  }
}

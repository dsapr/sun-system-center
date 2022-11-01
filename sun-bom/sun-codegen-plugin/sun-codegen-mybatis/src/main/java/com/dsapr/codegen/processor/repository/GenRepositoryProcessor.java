package com.dsapr.codegen.processor.repository;


import com.dsapr.codegen.processor.BaseCodeGenProcessor;
import com.dsapr.codegen.spi.CodeGenProcessor;
import com.dsapr.mybatis.support.BaseRepository;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;

/**
 * @author gim
 */
@AutoService(value = CodeGenProcessor.class)
public class GenRepositoryProcessor extends BaseCodeGenProcessor {

  public static final String REPOSITORY_SUFFIX = "Repository";

  @Override
  protected void generateClass(TypeElement typeElement, RoundEnvironment roundEnvironment) {
    // 类名 ---> xxx + Repository
    String className = typeElement.getSimpleName() + REPOSITORY_SUFFIX;
    TypeSpec.Builder typeSpecBuilder = TypeSpec.interfaceBuilder(className)
         // 实现接口        // 生成包含泛型的类
        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(BaseRepository.class), ClassName.get(typeElement)))
        .addModifiers(Modifier.PUBLIC);

    // 生成 java 源文件
    genJavaSourceFile(generatePackage(typeElement),typeElement.getAnnotation(GenRepository.class).sourcePath(),typeSpecBuilder);
  }

  @Override
  public Class<? extends Annotation> getAnnotation() {
    return GenRepository.class;
  }

  @Override
  public String generatePackage(TypeElement typeElement) {
    return typeElement.getAnnotation(GenRepository.class).pkgName();
  }
}

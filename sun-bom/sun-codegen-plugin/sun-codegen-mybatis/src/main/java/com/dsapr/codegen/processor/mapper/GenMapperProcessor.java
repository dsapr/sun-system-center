package com.dsapr.codegen.processor.mapper;

import com.google.auto.service.AutoService;
import com.dsapr.codegen.processor.BaseCodeGenProcessor;
import com.dsapr.codegen.processor.DefaultNameContext;
import com.dsapr.codegen.spi.CodeGenProcessor;
import com.dsapr.codegen.util.StringUtils;
import com.dsapr.common.mapper.DateMapper;
import com.dsapr.common.mapper.GenericEnumMapper;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.Annotation;
import java.util.Optional;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: Gim
 * @Date: 2019/11/25 14:14
 * @Description:
 */
@AutoService(value = CodeGenProcessor.class)
public class GenMapperProcessor extends BaseCodeGenProcessor {

    public static final String SUFFIX = "Mapper";

    @Override
    protected void generateClass(TypeElement typeElement, RoundEnvironment roundEnvironment) {
        // 生成类名
        String className = typeElement.getSimpleName() + SUFFIX;
        // 生成包名
        String packageName = typeElement.getAnnotation(GenMapper.class).pkgName();
        // 生成注解对象
        AnnotationSpec mapperAnnotation = AnnotationSpec.builder(Mapper.class)
                .addMember("uses", "$T.class", GenericEnumMapper.class)
                .addMember("uses", "$T.class", DateMapper.class)
                .build();
        // 生成类、接口、枚举对象
        TypeSpec.Builder typeSpecBuilder = TypeSpec.interfaceBuilder(className)
                .addAnnotation(mapperAnnotation)
                .addModifiers(Modifier.PUBLIC);
        // 配置生成成员变量
        FieldSpec instance = FieldSpec
                // 根据包名、类名获取类型 命名为"INSTANCE"
                .builder(ClassName.get(packageName, className), "INSTANCE")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$T.getMapper($T.class)",
                        Mappers.class, ClassName.get(packageName, className))
                .build();
        // 在类中加入此成员变量
        typeSpecBuilder.addField(instance);
        DefaultNameContext nameContext = getNameContext(typeElement);
        // dto to entity method
        Optional<MethodSpec> dtoToEntityMethod = dtoToEntityMethod(typeElement, nameContext);
        dtoToEntityMethod.ifPresent(m -> typeSpecBuilder.addMethod(m));
        // request to updater
        Optional<MethodSpec> request2UpdaterMethod = request2UpdaterMethod(nameContext);
        request2UpdaterMethod.ifPresent(m -> typeSpecBuilder.addMethod(m));
        // request to dto
        Optional<MethodSpec> request2DtoMethod = request2DtoMethod(nameContext);
        request2DtoMethod.ifPresent(m -> typeSpecBuilder.addMethod(m));
        // request to query
        Optional<MethodSpec> request2QueryMethod = request2QueryMethod(nameContext);
        request2QueryMethod.ifPresent(m -> typeSpecBuilder.addMethod(m));
        // vo to response
        Optional<MethodSpec> vo2ResponseMethod = vo2ResponseMethod(nameContext);
        vo2ResponseMethod.ifPresent(m -> typeSpecBuilder.addMethod(m));
        // vo to customResponse
        Optional<MethodSpec> vo2CustomResponseMethod = vo2CustomResponseMethod(nameContext);
        vo2CustomResponseMethod.ifPresent(m -> typeSpecBuilder.addMethod(m));

        // 生成 java 源文件
        genJavaSourceFile(generatePackage(typeElement),
                typeElement.getAnnotation(GenMapper.class).sourcePath(), typeSpecBuilder);
    }


    @Override
    public Class<? extends Annotation> getAnnotation() {
        return GenMapper.class;
    }

    @Override
    public String generatePackage(TypeElement typeElement) {
        return typeElement.getAnnotation(GenMapper.class).pkgName();
    }

    private Optional<MethodSpec> dtoToEntityMethod(TypeElement typeElement, DefaultNameContext nameContext) {
        boolean containsNull = StringUtils.containsNull(nameContext.getCreatorPackageName());
        if (!containsNull) {
            return Optional.of(MethodSpec
                    .methodBuilder("dtoToEntity")
                    .returns(ClassName.get(typeElement))
                    .addParameter(
                            ClassName.get(nameContext.getCreatorPackageName(), nameContext.getCreatorClassName()),
                            "dto")
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .build());
        }
        return Optional.empty();
    }

    private Optional<MethodSpec> request2UpdaterMethod(DefaultNameContext nameContext) {
        boolean containsNull = StringUtils.containsNull(nameContext.getUpdaterPackageName(), nameContext.getUpdatePackageName());
        if (!containsNull) {
            return Optional.of(MethodSpec
                    .methodBuilder("request2Updater")
                    .returns(
                            ClassName.get(nameContext.getUpdaterPackageName(), nameContext.getUpdaterClassName()))
                    .addParameter(
                            ClassName.get(nameContext.getUpdatePackageName(), nameContext.getUpdateClassName()),
                            "request")
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .build());
        }
        return Optional.empty();
    }

    private Optional<MethodSpec> request2DtoMethod(DefaultNameContext nameContext) {
        boolean containsNull = StringUtils.containsNull(nameContext.getCreatorPackageName(), nameContext.getCreatePackageName());
        if (!containsNull) {
            return Optional.of(MethodSpec
                    .methodBuilder("request2Dto")
                    .returns(
                            ClassName.get(nameContext.getCreatorPackageName(), nameContext.getCreatorClassName()))
                    .addParameter(
                            ClassName.get(nameContext.getCreatePackageName(), nameContext.getCreateClassName()),
                            "request")
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .build());
        }
        return Optional.empty();
    }

    private Optional<MethodSpec> request2QueryMethod(DefaultNameContext nameContext) {
        boolean containsNull = StringUtils.containsNull(nameContext.getQueryPackageName(), nameContext.getQueryRequestPackageName());
        if (!containsNull) {
            return Optional.of(MethodSpec
                    .methodBuilder("request2Query")
                    .returns(
                            ClassName.get(nameContext.getQueryPackageName(), nameContext.getQueryClassName()))
                    .addParameter(ClassName.get(nameContext.getQueryRequestPackageName(),
                            nameContext.getQueryRequestClassName()), "request")
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .build());
        }
        return Optional.empty();
    }

    private Optional<MethodSpec> vo2ResponseMethod(DefaultNameContext nameContext) {
        boolean containsNull = StringUtils.containsNull(nameContext.getResponsePackageName(), nameContext.getVoPackageName());
        if (!containsNull) {
            return Optional.of(MethodSpec
                    .methodBuilder("vo2Response")
                    .returns(ClassName.get(nameContext.getResponsePackageName(),
                            nameContext.getResponseClassName()))
                    .addParameter(ClassName.get(nameContext.getVoPackageName(), nameContext.getVoClassName()),
                            "vo")
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .build());
        }
        return Optional.empty();
    }

    private Optional<MethodSpec> vo2CustomResponseMethod(DefaultNameContext nameContext) {
        boolean containsNull = StringUtils.containsNull(nameContext.getResponsePackageName(), nameContext.getVoPackageName());
        if (!containsNull) {
            return Optional.of(MethodSpec
                    .methodBuilder("vo2CustomResponse")
                    .returns(ClassName.get(nameContext.getResponsePackageName(),
                            nameContext.getResponseClassName()))
                    .addParameter(ClassName.get(nameContext.getVoPackageName(), nameContext.getVoClassName()),
                            "vo")
                    .addCode(
                            CodeBlock.of("$T response = vo2Response(vo);\n",
                                    ClassName.get(nameContext.getResponsePackageName(),
                                            nameContext.getResponseClassName()))
                    )
                    .addCode(
                            CodeBlock.of("return response;")
                    )
                    .addModifiers(Modifier.PUBLIC, Modifier.DEFAULT)
                    .build());
        }
        return Optional.empty();
    }
}

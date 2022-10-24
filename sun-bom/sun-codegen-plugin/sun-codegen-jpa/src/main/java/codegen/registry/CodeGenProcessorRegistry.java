package codegen.registry;

import com.dsapr.codegen.spi.CodeGenProcessor;
import com.google.common.collect.Maps;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * @author gim 通过SPI 加载所有的CodeGenProcessor 识别要处理的annotation标记类
 */
public final class CodeGenProcessorRegistry {

  private static Map<String, ? extends CodeGenProcessor> PROCESSORS;


  private CodeGenProcessorRegistry() {
    throw new UnsupportedOperationException();
  }

  /**
   * 注解处理器要处理的注解集合
   *
   * @return
   */
  public static Set<String> getSupportedAnnotations() {
    return PROCESSORS.keySet();
  }

  public static CodeGenProcessor find(String annotationClassName) {
    return PROCESSORS.get(annotationClassName);
  }

  /**
   * spi 加载所有的processor
   *
   * @return
   */
  public static void initProcessors() {
    final Map<String, CodeGenProcessor> map = Maps.newLinkedHashMap();
    // 通过 spi 获取所有 processor 实现
    ServiceLoader<CodeGenProcessor> processors = ServiceLoader.load(CodeGenProcessor.class,CodeGenProcessor.class.getClassLoader());
    Iterator<CodeGenProcessor> iterator = processors.iterator();
    while (iterator.hasNext()) {
      CodeGenProcessor next = iterator.next();
      // 调用方法获取 processor 对应的注解类
      Class<? extends Annotation> annotation = next.getAnnotation();
      // 放入 map
      map.put(annotation.getName(), next);
    }
    PROCESSORS = map;
  }

}

#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.config;

import com.dsapr.common.constants.CodeEnum;
import com.dsapr.common.exception.BusinessException;
import com.dsapr.common.exception.SystemException;
import com.dsapr.common.model.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

  @ExceptionHandler(BusinessException.class)
  public JsonObject handleBusinessException(BusinessException e){
    log.error("业务异常",e);
    return JsonObject.res(e.getMsg(),e.getData());
  }

  @ExceptionHandler(SystemException.class)
  public JsonObject handleSystemException(SystemException e){
    log.error("系统异常",e);
    return JsonObject.fail(CodeEnum.SystemError);
  }

  @ExceptionHandler(Exception.class)
  public JsonObject handleException(Exception e){
    log.error("系统异常",e);
    return JsonObject.fail(CodeEnum.SystemError);
  }


}

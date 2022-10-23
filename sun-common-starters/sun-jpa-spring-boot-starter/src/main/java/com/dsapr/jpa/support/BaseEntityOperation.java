package com.dsapr.jpa.support;

import static org.springframework.util.CollectionUtils.isEmpty;

import com.dsapr.common.exception.ValidationException;
import com.dsapr.common.model.ValidateResult;
import com.dsapr.common.validator.ValidateGroup;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

/**
 * @author gim 2022/3/5 10:07 下午
 */
public abstract class BaseEntityOperation implements com.dsapr.jpa.support.EntityOperation {

  static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  public <T> void doValidate(T t, Class<? extends ValidateGroup> group) {
    Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, group, Default.class);
    if (!isEmpty(constraintViolations)) {
      List<ValidateResult> results = constraintViolations.stream()
          .map(cv -> new ValidateResult(cv.getPropertyPath().toString(), cv.getMessage()))
          .collect(Collectors.toList());
      throw new ValidationException(results);
    }
  }
}

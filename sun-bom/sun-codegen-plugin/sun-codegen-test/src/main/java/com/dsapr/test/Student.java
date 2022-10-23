package com.dsapr.test;

import com.dsapr.codegen.processor.api.GenQueryRequest;
import com.dsapr.codegen.processor.api.GenResponse;
import com.dsapr.jpa.support.BaseJpaAggregate;
import com.dsapr.test.Constants;
import com.dsapr.codegen.processor.api.GenCreateRequest;
import com.dsapr.codegen.processor.api.GenFeign;
import com.dsapr.codegen.processor.api.GenQueryRequest;
import com.dsapr.codegen.processor.api.GenResponse;
import com.dsapr.codegen.processor.api.GenUpdateRequest;
import com.dsapr.codegen.processor.controller.GenController;
import com.dsapr.codegen.processor.creator.GenCreator;
import com.dsapr.codegen.processor.mapper.GenMapper;
import com.dsapr.codegen.processor.query.GenQuery;
import com.dsapr.codegen.processor.repository.GenRepository;
import com.dsapr.codegen.processor.service.GenService;
import com.dsapr.codegen.processor.creator.IgnoreCreator;
import com.dsapr.codegen.processor.updater.IgnoreUpdater;
import com.dsapr.codegen.processor.service.GenServiceImpl;
import com.dsapr.codegen.processor.updater.GenUpdater;
import com.dsapr.codegen.processor.vo.GenVo;
import com.dsapr.common.constants.ValidStatus;
import com.dsapr.jpa.converter.ValidStatusConverter;
import com.dsapr.jpa.support.BaseJpaAggregate;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@GenVo(pkgName = "com.dsapr.vo")
@Data
public class Student extends BaseJpaAggregate {

  @Convert(converter = ValidStatusConverter.class)
  @IgnoreUpdater
  @IgnoreCreator
  private ValidStatus validStatus;

  public void init() {
    setValidStatus(ValidStatus.VALID);
  }

  public void valid(){
    setValidStatus(ValidStatus.VALID);
  }

  public void invalid(){
    setValidStatus(ValidStatus.INVALID);
  }
}

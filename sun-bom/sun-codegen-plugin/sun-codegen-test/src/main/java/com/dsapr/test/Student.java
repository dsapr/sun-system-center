package com.dsapr.test;

import com.dsapr.codegen.processor.api.GenQueryRequest;
import com.dsapr.codegen.processor.creator.GenCreator;
import com.dsapr.codegen.processor.mapper.GenMapper;
import com.dsapr.codegen.processor.query.GenQuery;
import com.dsapr.codegen.processor.repository.GenRepository;
import com.dsapr.codegen.processor.service.GenService;
import com.dsapr.codegen.processor.service.GenServiceImpl;
import com.dsapr.codegen.processor.updater.GenUpdater;
import com.dsapr.jpa.support.BaseJpaAggregate;
import lombok.Data;

@GenMapper(pkgName = "com.dsapr.vo")
@GenQueryRequest(pkgName = "com.dsapr.vo")
@GenQuery(pkgName = "com.dsapr.vo")
@GenRepository(pkgName = "com.dsapr.vo")
@GenCreator(pkgName = "com.dsapr.vo")
@GenUpdater(pkgName = "com.dsapr.vo")
@GenService(pkgName = "com.dsapr.vo")
@GenServiceImpl(pkgName = "com.dsapr.vo")
@Data
public class Student extends BaseJpaAggregate {
  private String name;

  public void init() {
  }

  public void valid() {
  }

  public void invalid() {
  }
}

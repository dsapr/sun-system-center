package com.dsapr.jpa.support;

import org.springframework.data.repository.CrudRepository;

/**
 * @author gim 2022/3/5 9:52 下午
 */
@SuppressWarnings("unchecked")
public abstract class EntityOperations {

  public static <T, ID> com.dsapr.jpa.support.EntityUpdater<T, ID> doUpdate(CrudRepository<T, ID> repository) {
    return new com.dsapr.jpa.support.EntityUpdater<>(repository);
  }

  public static <T, ID> com.dsapr.jpa.support.EntityCreator<T, ID> doCreate(CrudRepository<T, ID> repository) {
    return new com.dsapr.jpa.support.EntityCreator(repository);
  }


}

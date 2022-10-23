package com.dsapr.jpa.support;

import com.dsapr.jpa.support.Executor;

import java.util.function.Consumer;

/**
 * @author gim 2022/1/28 9:10 下午
 */
public interface UpdateHandler<T>{

  Executor<T> update(Consumer<T> consumer);

}

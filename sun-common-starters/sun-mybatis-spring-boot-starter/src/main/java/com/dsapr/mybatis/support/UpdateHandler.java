package com.dsapr.mybatis.support;

import java.util.function.Consumer;

public interface UpdateHandler<T>{

  Executor<T> update(Consumer<T> consumer);

}

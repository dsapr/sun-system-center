package com.dsapr.mybatis.support;

import java.util.function.Supplier;

public interface Create<T> {

  UpdateHandler<T> create(Supplier<T> supplier);

}

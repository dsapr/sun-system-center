package com.dsapr.jpa.support;

import java.util.function.Supplier;

/**
 * @author gim 2022/1/28 9:55 下午
 */
public interface Create<T> {

  com.dsapr.jpa.support.UpdateHandler<T> create(Supplier<T> supplier);

}

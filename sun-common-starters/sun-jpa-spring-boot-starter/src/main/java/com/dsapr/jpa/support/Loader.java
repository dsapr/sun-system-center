package com.dsapr.jpa.support;

import java.util.function.Supplier;

/**
 * @author gim 2022/1/28 9:07 下午
 */
public interface Loader<T,ID> {

  com.dsapr.jpa.support.UpdateHandler<T> loadById(ID id);

  com.dsapr.jpa.support.UpdateHandler<T> load(Supplier<T> t);

}

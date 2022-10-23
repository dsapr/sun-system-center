package com.dsapr.jpa.support;

import com.dsapr.jpa.support.Create;
import com.dsapr.jpa.support.Executor;
import com.google.common.base.Preconditions;
import com.dsapr.common.validator.CreateGroup;
import io.vavr.control.Try;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;

/**
 * @author gim 2022/3/5 9:54 下午
 */
@Slf4j
public class EntityCreator<T, ID> extends com.dsapr.jpa.support.BaseEntityOperation implements Create<T>, com.dsapr.jpa.support.UpdateHandler<T>, com.dsapr.jpa.support.Executor<T> {

  private final CrudRepository<T, ID> repository;
  private T t;
  private Consumer<T> successHook = t -> log.info("save success");
  private Consumer<? super Throwable> errorHook = e -> e.printStackTrace();

  public EntityCreator(CrudRepository<T, ID> repository) {
    this.repository = repository;
  }


  @Override
  public Executor<T> errorHook(Consumer<? super Throwable> consumer) {
    this.errorHook = consumer;
    return this;
  }

  @Override
  public UpdateHandler<T> create(Supplier<T> supplier) {
    this.t = supplier.get();
    return this;
  }

  @Override
  public Executor<T> update(Consumer<T> consumer) {
    Preconditions.checkArgument(Objects.nonNull(t), "entity must supply");
    consumer.accept(this.t);
    return this;
  }

  @Override
  public Optional<T> execute() {
    doValidate(this.t, CreateGroup.class);
    T save = Try.of(() -> repository.save(t))
        .onSuccess(successHook)
        .onFailure(errorHook).getOrNull();
    return Optional.ofNullable(save);
  }

  @Override
  public Executor<T> successHook(Consumer<T> consumer) {
    this.successHook = consumer;
    return this;
  }

}


package com.dsapr.mybatis.support;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dsapr.common.validator.CreateGroup;
import com.google.common.base.Preconditions;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public class EntityCreator<T> extends BaseEntityOperation implements Create<T>, UpdateHandler<T>,
    Executor<T> {

  private final BaseMapper<T> baseMapper;
  private T t;
  private Consumer<T> successHook = t -> log.info("save success");
  private Consumer<? super Throwable> errorHook = e -> e.printStackTrace();

  public EntityCreator(BaseMapper<T> baseMapper) {
    this.baseMapper = baseMapper;
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
    T save = Try.of(() -> {
          baseMapper.insert(t);
          return this.t;
        })
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


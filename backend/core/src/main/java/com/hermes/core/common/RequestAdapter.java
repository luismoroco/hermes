package com.hermes.core.common;

import com.hermes.core.common.mapping.Mapper;

import java.util.Map;

public interface RequestAdapter<T> {
  default T buildRequest() {
    return Mapper.get().map(this, this.getTargetClass());
  }

  default T buildRequest(Map<String, Object> overrideKeys) {
    T result = this.buildRequest();

    overrideKeys.forEach((key, value) -> {
      try {
        var field = result.getClass().getDeclaredField(key);
        field.setAccessible(true);
        field.set(result, value);
      } catch (NoSuchFieldException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    });

    return result;
  }

  Class<T> getTargetClass();
}

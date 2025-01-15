package com.hermes.core.common;

import com.hermes.core.common.mapping.Mapper;

public interface Model {
  default <T> T map(Class<T> clazz) {
    return Mapper.get().map(this, clazz);
  }
}

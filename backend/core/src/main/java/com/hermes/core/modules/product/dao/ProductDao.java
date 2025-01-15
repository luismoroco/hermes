package com.hermes.core.modules.product.dao;

import com.hermes.core.modules.product.model.Product;
import reactor.core.publisher.Mono;

public interface ProductDao {
  Mono<Product> findById(Long productId);
  Mono<Boolean> existByName(String productName);
  Mono<Product> save(Product product);
}

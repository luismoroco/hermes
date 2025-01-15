package com.hermes.core.modules.product.dao;

import com.hermes.core.modules.product.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductDao {
  Mono<Product> findById(Long productId);
  Mono<Boolean> existByName(String productName);
  Mono<Product> save(Product product);
  Flux<Product> findAllByIdIn(List<Long> productIds);
}

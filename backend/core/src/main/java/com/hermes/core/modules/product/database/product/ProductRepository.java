package com.hermes.core.modules.product.database.product;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends R2dbcRepository<ProductEntity, Long> {
  Mono<Boolean> existsByName(String name);
}

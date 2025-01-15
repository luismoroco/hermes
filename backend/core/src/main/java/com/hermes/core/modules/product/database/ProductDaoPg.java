package com.hermes.core.modules.product.database;

import com.hermes.core.common.mapping.Mapper;
import com.hermes.core.modules.product.dao.ProductDao;
import com.hermes.core.modules.product.database.product.ProductEntity;
import com.hermes.core.modules.product.database.product.ProductRepository;
import com.hermes.core.modules.product.model.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Qualifier("postgresql")
public class ProductDaoPg implements ProductDao {
  private final ProductRepository repository;

  public ProductDaoPg(ProductRepository repository) {
    this.repository = repository;
  }

  @Override
  public Mono<Product> findById(Long productId) {
    return this.repository.findById(productId).map(ProductEntity::toModel);
  }

  @Override
  public Mono<Boolean> existByName(String productName) {
    return this.repository.existsByName(productName);
  }

  @Override
  public Mono<Product> save(Product product) {
    var productEntity = Mapper.get().map(product, ProductEntity.class);
    return this.repository.save(productEntity).map(ProductEntity::toModel);
  }
}

package com.hermes.core.modules.product;

import com.hermes.core.common.error.exc.BadRequestException;
import com.hermes.core.modules.product.dao.ProductDao;
import com.hermes.core.modules.product.model.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
  private final ProductDao dao;

  public ProductService(
    @Qualifier("postgresql") ProductDao dao
  ) {
    this.dao = dao;
  }

  public Mono<Void> existProductByName(String productName) {
    return this.dao.existByName(productName)
      .flatMap(productExist -> Boolean.TRUE.equals(productExist)
        ? Mono.error(new BadRequestException("Product already exist"))
        : Mono.empty());
  }

  public Mono<Product> saveProduct(Product product) {
    return this.dao.save(product);
  }

  public Mono<Product> findProductById(Long productId) {
    return this.dao.findById(productId)
      .switchIfEmpty(Mono.error(new BadRequestException("Product Not found")));
  }
}

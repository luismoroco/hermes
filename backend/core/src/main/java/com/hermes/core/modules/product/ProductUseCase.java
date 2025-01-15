package com.hermes.core.modules.product;

import com.hermes.core.modules.product.model.Product;
import com.hermes.core.modules.product.request.CreateProductRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
public class ProductUseCase {
  private final ProductService service;

  public ProductUseCase(ProductService service) {
    this.service = service;
  }

  @Transactional
  public Mono<Product> createProduct(final CreateProductRequest request) {
    return this.service.existProductByName(request.getName())
      .then(Mono.defer(() -> {
        var product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setEnabled(request.getEnabled());
        product.setFeatured(request.getFeatured());
        product.setStock(request.getStock());
        product.setArchived(Boolean.FALSE);

        return this.service.saveProduct(product);
      }));
  }
}

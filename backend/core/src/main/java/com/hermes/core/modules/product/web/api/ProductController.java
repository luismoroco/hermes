package com.hermes.core.modules.product.web.api;

import com.hermes.core.modules.product.ProductUseCase;
import com.hermes.core.modules.product.model.Product;
import com.hermes.core.modules.product.web.validator.CreateProductWebRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductUseCase useCase;

  public ProductController(ProductUseCase useCase) {
    this.useCase = useCase;
  }

  @PostMapping("")
  @PreAuthorize("hasRole('MANAGER')")
  public Mono<ResponseEntity<Product>> createProduct(@Valid @RequestBody final CreateProductWebRequest webRequest) {
    return this.useCase.createProduct(webRequest.buildRequest())
      .map(product -> ResponseEntity.status(HttpStatus.CREATED).body(product));
  }
}

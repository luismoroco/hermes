package com.hermes.core.modules.product.web.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  @PostMapping("")
  @PreAuthorize("hasRole('MANAGER')")
  public Mono<ResponseEntity<String>> createProduct() {
    return Mono.just(ResponseEntity.ok().body("success"));
  }
}

package com.jane.redis_performance.controller;

import com.jane.redis_performance.entity.Product;
import com.jane.redis_performance.service.ProductServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductControllerV1 {

    private ProductServiceV1 productService;

    @Autowired
    public void setProductService(ProductServiceV1 productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Mono<Product> getProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }

    @PutMapping("/{id}")
    public Mono<Product> updateProduct(@PathVariable int id, @RequestBody Mono<Product> productMono) {
        return productService.updateProduct(id, productMono);
    }
}

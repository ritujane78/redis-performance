package com.jane.redis_performance.controller;

import com.jane.redis_performance.entity.Product;
import com.jane.redis_performance.service.ProductServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product/v2")
public class ProductControllerV2 {

    private ProductServiceV2 productService;

    @Autowired
    public void setProductService(ProductServiceV2 productService) {
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


    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable int id) {
        return productService.deleteProduct(id);
    }
}
package com.jane.redis_performance.service;

import com.jane.redis_performance.entity.Product;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<Product> getProduct(int id);
    Mono<Product> updateProduct(int id, Mono<Product> productMono);
}

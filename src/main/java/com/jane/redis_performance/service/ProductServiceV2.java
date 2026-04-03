package com.jane.redis_performance.service;

import com.jane.redis_performance.entity.Product;
import reactor.core.publisher.Mono;

public interface ProductServiceV2 {
    Mono<Product> getProduct(int id);
    Mono<Product> updateProduct(int id, Mono<Product> product);
    Mono<Void> deleteProduct(int id);
}

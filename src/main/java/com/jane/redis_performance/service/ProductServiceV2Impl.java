package com.jane.redis_performance.service;

import com.jane.redis_performance.entity.Product;
import com.jane.redis_performance.service.util.CacheTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceV2Impl implements ProductServiceV2 {
    @Autowired
    private CacheTemplate<Integer, Product> cacheTemplate;

    @Override
    public Mono<Product> getProduct(int id) {
        return this.cacheTemplate.get(id);
    }

    @Override
    public Mono<Product> updateProduct(int id, Mono<Product> product) {
        return product
                .flatMap(p -> this.cacheTemplate.update(id, p));
    }

    public Mono<Void> deleteProduct(int id) {
        return this.cacheTemplate.delete(id);
    }
}

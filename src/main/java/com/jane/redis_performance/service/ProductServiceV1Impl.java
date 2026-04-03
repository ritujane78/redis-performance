package com.jane.redis_performance.service;

import com.jane.redis_performance.entity.Product;
import com.jane.redis_performance.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceV1Impl implements ProductServiceV1 {
    private ProductRepository productRepository;

    public ProductServiceV1Impl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Mono<Product> getProduct(int id) {
        return productRepository.findById(id);
    }

    @Override
    public Mono<Product> updateProduct(int id, Mono<Product> productMono) {
        return productRepository.findById(id)
                .flatMap(p -> productMono.doOnNext(pr -> pr.setId(id)))
                .flatMap(pr -> productRepository.save(pr));
    }
}

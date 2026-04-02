package com.jane.redis_performance.repository;

import com.jane.redis_performance.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
}

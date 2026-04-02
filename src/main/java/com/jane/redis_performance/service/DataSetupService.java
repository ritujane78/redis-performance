package com.jane.redis_performance.service;

import com.jane.redis_performance.entity.Product;
import com.jane.redis_performance.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

     @Value("classpath:schema.sql")
     private Resource resource;

    @Override
    public void run(String... args) throws Exception {
        String sql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

        System.out.println(sql);
        Mono<Void> insert = Flux.range(1, 1000)
                .map(i -> new Product(null, "product" + i, ThreadLocalRandom.current().nextInt(1, 100)))
                .collectList()
                .flatMapMany(p -> productRepository.saveAll(p))
                .then();
    r2dbcEntityTemplate.getDatabaseClient()
            .sql(sql)
            .then()
            .then(insert)
            .doFinally(s -> System.out.println("Done"))
            .subscribe();
    }
}

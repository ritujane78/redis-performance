package com.jane.redis_performance.service;

import jakarta.annotation.PostConstruct;
import net.bytebuddy.asm.Advice;
import org.redisson.api.*;
import org.redisson.client.codec.IntegerCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import javax.swing.text.DateFormatter;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductVisitService {
    @Autowired
    private RedissonReactiveClient client;

    private Sinks.Many<Integer> sink;

    public ProductVisitService() {
        this.sink = Sinks.many().unicast().onBackpressureBuffer();
    }

    @PostConstruct
    public void init() {
        this.sink
                .asFlux()
                .buffer(Duration.ofSeconds(3))
                .map(l -> l.stream().collect(
                        Collectors.groupingBy(
                            Function.identity(),
                                Collectors.counting()
                        )
                ))
                .flatMap(this::updateBatch)
                .subscribe();
    }

    public void addVisit(Integer id) {
        this.sink.tryEmitNext(id);
    }

    private Mono<Void> updateBatch(Map<Integer, Long> map){
        RBatchReactive batch = this.client.createBatch(BatchOptions.defaults());
        String format = DateTimeFormatter.ofPattern("yyyyyMMDD").format(LocalDate.now());
        RScoredSortedSetReactive<Integer> set = batch.getScoredSortedSet("product:visit:" + format, IntegerCodec.INSTANCE);
        return Flux.fromIterable(map.entrySet())
                        .map(e -> set.addScore(e.getKey(), e.getValue()))
                                .then(batch.execute())
                .then();

    }
}

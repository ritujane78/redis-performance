package com.jane.redis_performance.service;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface BusinessMetricsService {
    Mono<Map<Integer, Double>> top3Products();

}

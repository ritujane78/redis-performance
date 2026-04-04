# Redis - performance

This project demonstrates how caching using Redis significantly improves application performance by reducing database calls and serving data directly from memory.

##  Overview

Modern applications often suffer from performance bottlenecks due to repeated database access. This project explores how integrating Redis as an in-memory cache layer helps:

- Reduce database load
- Improve response time
- Handle high concurrent traffic efficiently

Redis is an in-memory data store widely used for caching because of its speed and support for multiple data structures. :contentReference[oaicite:1]{index=1}

---

## Architecture

The project follows a **Cache-Aside (Lazy Loading) Pattern**:

1. Request comes in
2. Check Redis cache
    - If present → return cached data
    - If absent → fetch from DB
3. Store result in Redis
4. Return response

### Key Components

- **Spring Boot Application**
- **Redis (via Redisson)**
- **Reactive Programming (Project Reactor - Mono)**
- **MySQL Database (for persistent storage)**

---

## Features Implemented

###  Initial Setup
- Basic CRUD APIs using Spring Boot
- Database integration for product storage

###  Redis Integration
- Redis configured as caching layer
- Redisson client used for reactive operations
- Custom cache abstraction (`CacheTemplate`)

###  Cache Strategies
- Cache-aside pattern implementation
- Local cached map (`RLocalCachedMap`)
- Reactive Redis (`RMapReactive`)

###  Performance Optimization Iterations
- Transition from direct DB calls → cached responses
- Improved cache handling using templates
- Fixes for cache not persisting across requests
- Bean configuration fixes for `RedissonReactiveClient`

---

## Performance Testing (JMeter)

To validate the effectiveness of Redis caching, **Apache JMeter** is used for load testing.

### Test Scenarios

#### 1. Without Redis Cache
- All requests hit the database
- Higher latency
- Increased DB load

#### 2. With Redis Cache
- Frequently accessed data served from memory
- Reduced DB queries
- Faster response times

### Observations

- Significant drop in response time after caching
- Throughput increased under concurrent load
- Database pressure reduced drastically

### Running JMeter

1. Install JMeter
2. Create a `.jmx` test plan
3. Run:
```bash
   jmeter -n -t test-plan.jmx -l results.jtl
```
### Tech Stack

1. Java
2. Spring Boot
3. Redis
4. Redisson
5. Project Reactor
6. JMeter

### Key Learnings
1. Redis drastically improves read-heavy workloads
2. Cache invalidation is critical
3. Reactive programming requires careful handling of side effects
4. Benchmarking is essential to validate performance gains
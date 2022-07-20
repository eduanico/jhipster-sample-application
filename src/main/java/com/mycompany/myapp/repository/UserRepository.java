package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.User;
import java.time.Instant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data MongoDB repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";
    Mono<User> findOneByActivationKey(String activationKey);
    Flux<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);
    Mono<User> findOneByResetKey(String resetKey);

    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Mono<User> findOneByEmailIgnoreCase(String email);

    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Mono<User> findOneByLogin(String login);

    Flux<User> findAllByIdNotNull(Pageable pageable);

    Flux<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);

    Mono<Long> count();
}

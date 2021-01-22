package com.forum.repository;

import com.forum.repository.entity.Page;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;

@Repository
@Validated
public interface PageRepository extends ReactiveMongoRepository<Page, String>{
    public Mono<Page> findByPostIdAndPageNumber(String postId, String pageNumber);
    public Mono<Void> deleteByPostId(String postId);
}

package com.forum.repository;

import com.forum.repository.entity.Post;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends ReactiveMongoRepository<Post,String>{
}
package com.forum.service;

import com.forum.dto.PostDto;
import com.forum.exception.ApplicationException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {
    public Flux<PostDto> getAllPosts();
    public Mono<PostDto> getPostByIdAndPageNum(String postId, String pageNum);
    public Mono<PostDto> createPost(PostDto postDto, String userId) throws ApplicationException;
    public Mono<Void> deletePost(String postId) throws ApplicationException; 
}
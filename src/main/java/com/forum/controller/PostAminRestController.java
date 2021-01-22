package com.forum.controller;


import java.util.List;

import com.forum.dto.PostDto;
import com.forum.exception.ApplicationException;
import com.forum.service.PostService;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping(PostAminRestController.URL)
@Api(value = "Post Admin Rest Controller")
//TODO: need to add spring security to guard admin role only
/**
 * This controller contain all the admin function regarding to post functions
 */
public class PostAminRestController { 
    public final static String URL = "/rest/admin/post"; 
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired private PostService postService;

    @ApiOperation(value = "Get All Post without comments",consumes = "application/json", response = List.class)
    @GetMapping 
    public Mono<ResponseEntity<List<PostDto>>> getAllPost(){
        return postService.getAllPosts()
                            .collectList()
                            .flatMap(postDto -> {
                                return Mono.just(ResponseEntity.ok(postDto));
                            });
    }

    @PostMapping
    @ApiOperation(value = "Create Post by Admin for user",consumes = "application/json", response = PostDto.class)
    public Mono<ResponseEntity<PostDto>> createPost(@RequestParam("userId") String userId, @RequestBody PostDto postDto) throws ApplicationException{
        return postService.createPost(postDto, userId)
                            .flatMap(savedPostDto -> {
                                return Mono.just(ResponseEntity.ok(savedPostDto));
                            });
    }
    
    @DeleteMapping
    @ApiOperation(value = "Create Post by Admin for user",consumes = "application/json")
    public Mono<ResponseEntity<Void>> deletePost(@RequestParam("postId") String postId) throws ApplicationException{
        return postService.deletePost(postId)
                            .then(Mono.just(ResponseEntity.ok().build()));
    }

}
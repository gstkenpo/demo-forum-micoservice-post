package com.forum.controller;

import com.forum.dto.PostDto;
import com.forum.service.PostService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
@RestController
@RequestMapping(PostRestController.URL)
public class PostRestController {
    public static final String URL = "/rest/post";
    @Autowired private PostService postService;

    @GetMapping(path = "id")
    public Mono<ResponseEntity<PostDto>> getPostById(@RequestParam("id") String postId, @RequestParam(name = "pageNum", defaultValue = "1") String pageNum){
        if (StringUtils.isEmpty(postId)) return Mono.just(ResponseEntity.noContent().build());
        return postService.getPostByIdAndPageNum(postId, pageNum)
                            .flatMap(post -> {
                                return Mono.just(ResponseEntity.ok(post));
                            });
    }
}

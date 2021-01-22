package com.forum.service.Impl;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.forum.converter.PageConverter;
import com.forum.converter.PostConverter;
import com.forum.dto.CommentDto;
import com.forum.dto.PageDto;
import com.forum.dto.PostDto;
import com.forum.exception.ApplicationException;
import com.forum.repository.PageRepository;
import com.forum.repository.PostRepository;
import com.forum.repository.entity.Page;
import com.forum.repository.entity.Post;
import com.forum.service.PostService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostServiceImpl implements PostService {
    @Autowired private PostRepository postRepository;
    @Autowired private PageRepository pageRepository;
    @Autowired private PostConverter postConverter;
    @Autowired private PageConverter pageConverter;
    
    @Override
       public Flux<PostDto> getAllPosts() {
        return postRepository.findAll()
                            //.delayElements(Duration.ofSeconds(3l))
                            .flatMap(post -> {
                                String postId = post.getId();
                                return pageRepository.findByPostIdAndPageNumber(postId, Long.toString(1l))
                                                    //.delayElement(Duration.ofSeconds(3l))
                                                    .flatMap(page -> {
                                                        PostDto postDto = postConverter.convertFromEntity(post);
                                                        postDto.setPages(Arrays.asList(new PageDto[]{pageConverter.convertFromEntity(page)}));
                                                        return Mono.just(postDto);
                                                    }).defaultIfEmpty(PostDto.builder()
                                                                            .postTitle(post.getTitle())
                                                                            .userName(post.getUserName())
                                                                            .build());
                            });
    }

    @Override
    public Mono<PostDto> createPost(PostDto postDto, String userId) throws ApplicationException{
        if (postDto == null || StringUtils.isEmpty(userId)) return null;//TODO: throw application exception
        Post post = Post.builder()
                        .title(postDto.getPostTitle())
                        .userId(userId)
                        .userName(postDto.getUserName())
                        .build();
        if (postDto.getPages() == null || CollectionUtils.isEmpty(postDto.getPages().get(0).getComments())) return null;
        Page page = Page.builder()
                        .pageNumber(postDto.getPages().get(0).getPageNum())
                        .comments(postDto.getPages()
                                        .get(0)
                                        .getComments()
                                        .stream()
                                        .map(CommentDto::convertToEntity)
                                        .collect(Collectors.toList()))
                        .build();
        return postRepository.save(post)
                            .flatMap(savedPost -> {
                                String postId = savedPost.getId();
                                if (StringUtils.isEmpty(postId)) return Mono.error(new ApplicationException("msg"));
                                page.setPostId(postId);
                                return pageRepository.save(page)
                                                    .flatMap((savedPage) -> {
                                                        return Mono.just(postDto);
                                                    });
                            }).onErrorReturn(ApplicationException.class, null);
    }

    @Override
    public Mono<Void> deletePost(String postId) throws ApplicationException {
        if(StringUtils.isEmpty(postId)) return null;
        return postRepository.deleteById(postId)
                            .then(pageRepository.deleteByPostId(postId));
    }

    @Override
    public Mono<PostDto> getPostByIdAndPageNum(String postId, String pageNum) {
        if (StringUtils.isEmpty(postId)) return Mono.empty();
        return postRepository.findById(postId)
                            .map(postConverter::convertFromEntity)
                            .flatMap(postDto -> {
                                return pageRepository.findByPostIdAndPageNumber(postDto.getPostId(), pageNum)
                                                    .map(pageConverter::convertFromEntity)
                                                    .flatMap(pageDto -> {
                                                        postDto.setPages(Arrays.asList(new PageDto[]{pageDto}));
                                                        return Mono.just(postDto);
                                                    });
                            });
    }
}
package com.forum.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.forum.dto.CommentDto;
import com.forum.repository.PageRepository;
import com.forum.repository.PostRepository;
import com.forum.repository.entity.Comment;
import com.forum.repository.entity.Page;
import com.forum.repository.entity.Post;
import com.forum.service.Impl.PostServiceImpl;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@MockitoSettings(strictness = Strictness.LENIENT)
/**
strictness.LENIENT bypass UnnecessaryStubbingException
source: https://www.baeldung.com/mockito-unnecessary-stubbing-exception
**/
public class PostServiceTest {
    @InjectMocks private PostServiceImpl postServiceImpl;
    @Mock private PostRepository postRepository;
    @Mock private PageRepository pageRepository;

    @BeforeEach
    public void before(){
        Mockito.when(postRepository.findAll())
                .thenReturn(Flux.just(Post.builder()
                                        .id("id")
                                        .userId("userId")
                                        .userName("userName")
                                        .title("title")
                                        .build()));
        List<Comment> comments = Arrays.asList(new Comment[]{
                                                            Comment.builder().msg("msg1").build(), 
                                                            Comment.builder().msg("msg2").build()
                                                        });
        Mockito.when(pageRepository.findByPostIdAndPageNumber("id", "1"))
                .thenReturn(Mono.just(Page.builder()
                                           .id("id")
                                           .pageNumber("1")
                                           .comments(comments)
                                           .build()));
    }

    @Test
    public void testInjection(){
        assertNotNull(postRepository);
        assertNotNull(pageRepository);
        assertNotNull(postServiceImpl);
    }

    @Test
    public void testGetAllPosts(){
        StepVerifier.create(postServiceImpl.getAllPosts())
                    .assertNext(postDto -> {
                        assertNotNull(postDto);
                        assertTrue(StringUtils.equals(postDto.getPostTitle(), "title"));
                        assertTrue(StringUtils.equals(postDto.getUserName(), "userName"));
                        assertTrue(CollectionUtils.isNotEmpty(postDto.getPages()));
                        List<CommentDto> comments = postDto.getPages()
                                                        .stream()
                                                        .map(page -> {
                                                            return page.getComments();
                                                        }).flatMap(List::stream)
                                                        .collect(Collectors.toList());
                        assertTrue(CollectionUtils.isNotEmpty(comments));
                    });
    }

    @Test
    public void testCreatePost(){
    }
}

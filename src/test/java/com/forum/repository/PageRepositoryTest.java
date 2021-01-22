package com.forum.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.forum.repository.entity.Comment;
import com.forum.repository.entity.Page;
import com.forum.repository.entity.Post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import reactor.test.StepVerifier;

@DataMongoTest
public class PageRepositoryTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired private PageRepository pageRepository;
    @Autowired private PostRepository postRepository;
    private Post post;
    Validator validator;

    @BeforeEach
    public void init(){
        post = Post.builder().build();
        post = postRepository.save(post).block();
        Page page = createPage(post.getId(), "1");
        pageRepository.save(page).block();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testInjectedComponentsAreNotNull(){
        logger.error("This is testing");
        assertNotNull(pageRepository);
    }
    @Test
    public void testCreateNewPage(){
        Page page = createPage(post.getId(), "2");
        StepVerifier.create(pageRepository.save(page))
                    .assertNext(savedPage -> {
                        assertNotNull(savedPage.getId());
                    }).verifyComplete();
    }
    
    
    @Test
    public void testQueryPageById(){
        StepVerifier.create(pageRepository.findByPostIdAndPageNumber(post.getId(), "1"))
                    .assertNext(queryPage -> {
                        assertNotNull(queryPage);
                    }).verifyComplete();
        StepVerifier.create(pageRepository.findByPostIdAndPageNumber(post.getId(), "2"))
                    .expectNextCount(0)
                    .verifyComplete();
    }

    @Test
    public void testInsertComment(){
        Page page = pageRepository.findByPostIdAndPageNumber(post.getId(), "1").block();
        Comment comment = Comment.builder()
                                .userId("userId")
                                .userName("userName")
                                .msg("Testing msg")
                                .build();
        List<Comment> comments = new ArrayList<Comment>();
        comments.add(comment);
        page.setComments(comments);
        StepVerifier.create(pageRepository.save(page))
                    .assertNext(savedPage -> {
                        assertNotNull(savedPage.getComments());
                        assertTrue(savedPage.getComments().size() > 0);
                    }).verifyComplete();
    }

    @Test
    public void testInsertExceedNumOfComment(){
        Page page = pageRepository.findByPostIdAndPageNumber(post.getId(), "1").block();
        List<Comment> comments = new ArrayList<Comment>();
        //add 26 comments in this page
        for (int i = 0; i < 26; i++){
            comments.add(Comment.builder()
                                .msg("msg: " + i)
                                .userId("userId" + i)
                                .userName("userName" + i)
                                .build()
            );
        }
        page.setComments(comments);
        Set<ConstraintViolation<Page>> violations = validator.validate(page);
        assertTrue(!violations.isEmpty());
    }

    @Test
    public void testDeleteByPostId(){
        Page page = this.createPage("postId", "1");
        Page savedPage = pageRepository.save(page).block();
        String pageId = savedPage.getId();
        assertNotNull(pageId);
        pageRepository.deleteByPostId("postId").block();
        page = pageRepository.findById(pageId).block();
        assertNull(page);
    }

    private Page createPage(String postId, String pageNumber){
        return Page.builder()
                    .postId(postId)
                    .pageNumber(pageNumber)
                    .build();
    }
}

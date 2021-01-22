package com.forum.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.forum.repository.entity.Post;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import reactor.test.StepVerifier;
@DataMongoTest
public class PostRepositoryTest {
    @Autowired private PostRepository postRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testInjectedComponentsAreNotNull(){
        logger.error("This is testing");
        assertNotNull(postRepository);
    }

    @Test
    public void testSave(){
        Post post = createPost("Testing Post", "testUserId", "testUserName");
        StepVerifier.create(postRepository.save(post))
                    .assertNext(savedPost -> {
                        logger.debug("post id: " + savedPost.getId());
                        assertNotNull(savedPost.getId());
                    }).verifyComplete();
    }

    @Test
    public void testQueryById() {
        Post post = postRepository.save(createPost("Testing Post", "testUserId", "testUserName")).block();
        StepVerifier.create(postRepository.findById(post.getId()))
                    .expectNext(post)
                    .verifyComplete();
        
    }



    private Post createPost(String title, String userId, String userName){
        return Post.builder()
                        .title(title)
                        .userId(userId)
                        .userName(userName)
                        .build();
    }

}

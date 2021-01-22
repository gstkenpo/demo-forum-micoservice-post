package com.forum.controller;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(TestRestController.class)
public class TestRestControllerTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WebTestClient webClient;

    @BeforeTestClass
    public void beforeTestClass() {
        // this.webClient = WebTestClient.
    }

    @Test
    public void testNormalGet() throws Exception {
        this.createNormalGet("1");
    }

    @Test
    public void testMultipleGet() throws Exception {
        for (int i = 0; i < 30; i++) {
            final int index = i;
            new Thread(() -> {
                try {
                    this.createNormalGet(Integer.toString(index));
                } catch (Exception e) {
                    logger.error("" + e);
                }
            }).start();
        }
        Thread.sleep(10000);
    }

    private void createNormalGet(String testValue) throws Exception {
        webClient.get()
                .uri("http://localhost:8080/test?test=" + testValue)
                .accept(MediaType.ALL)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith((consumer) -> {
                    logger.info("main thread: " + Thread.currentThread().getName() + " " + consumer);
                });
    }
}

package com.forum.controller;

import java.time.Duration;

import org.jboss.logging.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(TestRestController.URL)
public class TestRestController {
    private final Logger logger = Logger.getLogger(this.getClass());
    public static final String URL = "test";

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<String> getHandler(@RequestParam("test") String test){
        logger.info("test value entered: " + test);
        String otherTest = "Yo: " + test;
        return Mono.just(test)
            .delayElement(Duration.ofMillis(Long.valueOf(2000)))
            .map((result) -> {
                return "Received test value: " + result + " " 
                + "handled in thread number: " + Thread.currentThread().getName() + " otherTest: " + otherTest;
            });
    }
}

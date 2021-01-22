package com.forum.controller;

import java.util.List;

import com.forum.exception.ApplicationException;
import com.forum.repository.entity.Comment;
import com.forum.repository.entity.Post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CommentController.URL)
public class CommentController {
    public final static String URL = "/rest/comment"; 

    @GetMapping
    public ResponseEntity<List<Comment>> getPostComments(Post post) throws ApplicationException{
        return null;
    }

    @PostMapping
    public ResponseEntity<List<Comment>> createPost(Post post) throws ApplicationException {
        return null;
    }
}

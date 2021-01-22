package com.forum.repository.entity;

import com.forum.dto.CommentDto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document("comments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Field(value = "user_id")
    private String userId;
    @Field(value = "user_name")
    private String userName;
    @Field(value = "msg")
    private String msg;

    public static final CommentDto convertToDto(Comment comment){
        return CommentDto.builder()
                        .userName(comment.getUserName())
                        .msg(comment.getMsg())
                        .build();
    }
}

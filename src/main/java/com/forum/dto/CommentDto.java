package com.forum.dto;

import com.forum.repository.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private String userName;
    private String msg;

    public static final Comment convertToEntity(CommentDto comment){
        return Comment.builder()
                    .userName(comment.getUserName())
                    .msg(comment.getMsg())
                    .build();
    }
}

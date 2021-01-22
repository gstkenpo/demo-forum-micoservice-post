package com.forum.repository.entity;

import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("page")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page {
    @Id
    private String id;
    @Field("post_id")
    private String postId;
    @Field("page_number")
    private String pageNumber;
    @Field("comments")
    @Size(max = 25)
    private List<Comment> comments;
}

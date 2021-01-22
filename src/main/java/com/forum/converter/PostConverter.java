package com.forum.converter;

import com.forum.dto.PostDto;
import com.forum.repository.entity.Post;

import org.springframework.stereotype.Component;

@Component
public class PostConverter extends Converter<Post, PostDto> {

    public PostConverter() {
        super(PostConverter::convertToEntity, PostConverter::convertToDto);
    }
    
    private static final Post convertToEntity(PostDto postDto){
        return Post.builder()
                    .id(postDto.getPostId())
                    .title(postDto.getPostTitle())
                    .userName(postDto.getUserName())
                    .build();
    }

    private static final PostDto convertToDto(Post post){
        return PostDto.builder()
                        .postId(post.getId())
                        .postTitle(post.getTitle())
                        .userName(post.getUserName())
                        .build();
    }
}

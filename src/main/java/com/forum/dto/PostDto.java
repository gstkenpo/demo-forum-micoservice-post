package com.forum.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    @NotBlank
    private String postId;
    @NotBlank
    private String postTitle;
    @NotBlank
    private String userName;
    private List<PageDto> pages;
}
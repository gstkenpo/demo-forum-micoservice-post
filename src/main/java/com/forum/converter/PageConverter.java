package com.forum.converter;

import java.util.stream.Collectors;

import com.forum.dto.CommentDto;
import com.forum.dto.PageDto;
import com.forum.repository.entity.Comment;
import com.forum.repository.entity.Page;

import org.springframework.stereotype.Component;

@Component
public class PageConverter extends Converter<Page, PageDto> {

    public PageConverter() {
        super(PageConverter::convertToEntity, PageConverter::convertToDto);
    }

    private static final Page convertToEntity(PageDto pageDto){
        return Page.builder()
                    .pageNumber(pageDto.getPageNum())
                    .comments(pageDto.getComments().stream().map(CommentDto::convertToEntity).collect(Collectors.toList()))
                    .build();
    }

    private static final PageDto convertToDto(Page page){
        return PageDto.builder()
                        .pageNum(page.getPageNumber())
                        .comments(page.getComments().stream().map(Comment::convertToDto).collect(Collectors.toList()))
                        .build();
    }
    
}

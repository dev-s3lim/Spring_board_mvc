package com.beyond.basic.post.dto;

import com.beyond.basic.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostDetailDto {
    private Long id;
    private String title;
    private String category;
    private String contents;
    private String authorEmail;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public static PostDetailDto fromEntity(Post post){
        return PostDetailDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .category(post.getCategory())
                .contents(post.getContents())
                .authorEmail(post.getAuthor().getEmail())
                .createdTime(post.getCreatedTime())
                .updatedTime(post.getUpdatedTime())
                .build();
    }
}

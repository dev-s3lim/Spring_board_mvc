package com.beyond.basic.post.dto;

import com.beyond.basic.author.domain.Author;
import com.beyond.basic.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class PostCreateDto {
    private String title;
    private String category;
    private String contents;
    private String delYn;
    @Builder.Default
    private String appointment = "N";
    private String appointmentTime;

    public Post toEntity(Author author, LocalDateTime appointmentTime) {
        return Post.builder()
                .title(this.title)
                .category(this.category)
                .contents(this.contents)
                .author(author)
                .appointment(this.appointment)
                .appointmentTime(appointmentTime)
                .delYn("N")
                .build();
    }
}

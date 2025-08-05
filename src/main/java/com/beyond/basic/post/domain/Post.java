package com.beyond.basic.post.domain;

import com.beyond.basic.author.domain.Author;
import com.beyond.basic.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString

public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String category; // 카테고리
    @Column(length = 3000)
    private String contents;
    @Builder.Default
    private String delYn = "N";
    @Builder.Default
    private String appointment = "N"; // 예약 게시글 여부
    private LocalDateTime appointmentTime; // 예약 게시글 시간
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id") // fk 관계성
    private Author author;

    public void updateAppointment (String appointment){
        this.appointment = appointment;
    }
}
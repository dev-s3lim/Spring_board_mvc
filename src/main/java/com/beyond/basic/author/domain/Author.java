package com.beyond.basic.author.domain;

import com.beyond.basic.author.dto.AuthorListDto;
import com.beyond.basic.common.domain.BaseTimeEntity;
import com.beyond.basic.post.domain.Post;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Builder
public class Author extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 50, unique = true, nullable = false)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<Post> postList = new ArrayList<>();

    @OneToOne (mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    public void updatePw(String password) {
        this.password = password;
    }

    public AuthorListDto listFromEntity() {
        return new AuthorListDto(this.id, this.name, this.email);
    }

}

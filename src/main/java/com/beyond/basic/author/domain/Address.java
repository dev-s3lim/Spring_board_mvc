package com.beyond.basic.author.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long city;
    private String street;
    private String zipcode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", unique = true)
    private Author author;
}

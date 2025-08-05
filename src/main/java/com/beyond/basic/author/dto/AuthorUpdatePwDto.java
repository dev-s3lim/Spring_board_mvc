package com.beyond.basic.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorUpdatePwDto {
    private String email;
    private String password;
}

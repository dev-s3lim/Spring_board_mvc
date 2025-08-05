package com.beyond.basic.author.dto;

import com.beyond.basic.author.domain.Author;
import com.beyond.basic.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorCreateDto {

    @NotEmpty(message = "이름을 입력해 주세요")
    private String name;
    @NotEmpty(message = "이메일을 입력해 주세요")
    private String email;
    @NotEmpty(message = "비밀번호를 입력해 주세요")
    @Size(min = 8, message = "비밀번호가 너무 짧습니다.")
    private String password;

    public Author authorToEntity() {
        return Author.builder()
                .name(this.name)
                .password(this.password)
                .email(this.email)
                .role(Role.USER)
                .build();
    }
}

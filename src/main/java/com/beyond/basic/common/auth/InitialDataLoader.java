package com.beyond.basic.common.auth;

import com.beyond.basic.author.domain.Author;
import com.beyond.basic.author.domain.Role;
import com.beyond.basic.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitialDataLoader implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (authorRepository.findByEmail("admin@naver.com").isPresent()){
            return;
        }
        Author author = Author.builder()
                .name("관리자")
                .email("admin@naver.com")
                .role(Role.ADMIN)
                .password(passwordEncoder.encode("12345678"))
                .build();
        authorRepository.save(author);
    }
}

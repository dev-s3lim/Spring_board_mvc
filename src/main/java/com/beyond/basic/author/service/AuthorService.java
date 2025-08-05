package com.beyond.basic.author.service;

import com.beyond.basic.author.domain.Author;
import com.beyond.basic.author.dto.*;
import com.beyond.basic.author.repository.AuthorRepository;
import com.beyond.basic.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(AuthorCreateDto authorCreateDto) {
        if (authorRepository.findByEmail(authorCreateDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(authorCreateDto.getPassword());
        authorCreateDto.setPassword(encodedPassword);

        Author author = authorCreateDto.authorToEntity();
        this.authorRepository.save(author);
    }

    @Transactional(readOnly = true)
    public List<AuthorListDto> findAll() {
        return authorRepository.findAll().stream().map(a -> a.listFromEntity()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AuthorDetailDto findById(Long id) throws NoSuchElementException {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        AuthorDetailDto authorDetailDto = AuthorDetailDto.fromEntity(author);
        return authorDetailDto;
    }

    @Transactional(readOnly = true)
    public Author findByEmail(String email) throws NoSuchElementException {
        return authorRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    public void updatePassword(AuthorUpdatePwDto authorUpdatePwDto) throws NoSuchElementException {
        authorRepository.findByEmail(authorUpdatePwDto.getEmail()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다.")).updatePw(authorUpdatePwDto.getPassword());
    }

    @Transactional(readOnly = true)
    public AuthorDetailDto myInfo(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Author author = authorRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        return AuthorDetailDto.fromEntity(author);
    }

    public void delete(Long id) throws NoSuchElementException {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        authorRepository.delete(author);
    }
}
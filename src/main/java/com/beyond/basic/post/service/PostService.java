package com.beyond.basic.post.service;

import com.beyond.basic.author.domain.Author;
import com.beyond.basic.author.repository.AuthorRepository;
import com.beyond.basic.post.domain.Post;
import com.beyond.basic.post.dto.PostCreateDto;
import com.beyond.basic.post.dto.PostDetailDto;
import com.beyond.basic.post.dto.PostListDto;
import com.beyond.basic.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public void save(PostCreateDto dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        System.out.println(email);
        Author author = authorRepository.findByEmail("admin@naver.com").orElseThrow(() -> new EntityNotFoundException("없는 ID입니다."));
        LocalDateTime appointmentTime = null;
        if (dto.getAppointment().equals("Y")){
            if (dto.getAppointmentTime() == null || dto.getAppointmentTime().isEmpty()) {
                throw new IllegalArgumentException("시간정보가 비어 있습니다.");
            }
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            appointmentTime = LocalDateTime.parse(dto.getAppointmentTime(), dateTimeFormatter);
        }
        postRepository.save(dto.toEntity(author, appointmentTime));
    }

    public PostDetailDto findById(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("없는 ID입니다."));
        return PostDetailDto.fromEntity(post);
    }

    public List<PostListDto> findAllNoPaging() {
        List<Post> postList = postRepository.findAllWithAuthor(); // fetch join 포함
        return postList.stream()
                .map(PostListDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<PostListDto> findAll(Pageable pageable){
        Page<Post> postList = postRepository.findAll(pageable);
        return postList.map(a -> PostListDto.fromEntity(a));
    }
}

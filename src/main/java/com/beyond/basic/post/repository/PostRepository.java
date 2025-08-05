package com.beyond.basic.post.repository;

import com.beyond.basic.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p inner join p.author")
    List<Post> findAllJoin();
    @Query("select p from Post p inner join fetch p.author")
    List<Post> findAllFetchJoin();

    Page<Post> findAllByDelYnAndAppointment(Pageable pageable, String delYn, String appointment);

    Page<Post> findAll(Specification<Post> specification, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN FETCH p.author WHERE p.delYn = 'N'")
    List<Post> findAllWithAuthor();

    List<Post> findByAppointment(String appointment);
}
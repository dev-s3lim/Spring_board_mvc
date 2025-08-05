package com.beyond.basic.post.controller;

import com.beyond.basic.post.dto.PostCreateDto;
import com.beyond.basic.post.dto.PostDetailDto;
import com.beyond.basic.post.dto.PostListDto;
import com.beyond.basic.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/create")
    public String postCreateScreen(){
        return "post/post_register";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute PostCreateDto dto) {
        postService.save(dto);
        return "redirect:/post/list";
    }

    @GetMapping("/list/all")
    public String postListAll() {
        List<PostListDto> postListDtos = postService.findAllNoPaging();
        return null;
    }

    @GetMapping("/list")
    public String postList(@PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<PostListDto> postListDtos = postService.findAll(pageable);
        model.addAttribute("postList", postListDtos);
        return "post/post_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        PostDetailDto PostDetaildtos = postService.findById(id);
        model.addAttribute("post", PostDetaildtos);
        return "post/post_detail";
    }
}
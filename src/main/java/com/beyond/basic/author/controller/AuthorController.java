package com.beyond.basic.author.controller;

import com.beyond.basic.author.dto.*;
import com.beyond.basic.author.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/create")
    public String createScreen() {
        return "author/author_register";
    }

    @PostMapping("/create")
    public String save(@Valid @ModelAttribute AuthorCreateDto authorCreateDto) {
        this.authorService.save(authorCreateDto);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<AuthorListDto> authorListDtoList = authorService.findAll();
        model.addAttribute("authorList", authorListDtoList);
        return "author/author_list";
    }

    @GetMapping("/detail/{id}")
    public String findById(@PathVariable Long id, Model model) {
        AuthorDetailDto authorDetailDto = authorService.findById(id);
        model.addAttribute("authorDetail", authorDetailDto);
        return "author/author_detail";
    }

    @PatchMapping("/updatepw")
    public ResponseEntity<?> updatePw(@RequestBody AuthorUpdatePwDto authorUpdatePwDto) {
        try {
            authorService.updatePassword(authorUpdatePwDto);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        authorService.delete(id);
        return "OK";
    }

    @GetMapping("/login")
    public String loginScreen() {
        return "author/author_login";
    }
}
